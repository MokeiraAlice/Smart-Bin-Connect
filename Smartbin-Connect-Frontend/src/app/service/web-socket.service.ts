import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import * as SockJS from 'sockjs-client';
import { Client, Message, over } from '@stomp/stompjs';
import { Observable, Subject, from } from 'rxjs';
import { SmartBin } from '../models/SmartBin';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient!: Client;
  private connectionStatus = new Subject<boolean>();
  private reconnectAttempts = 0;
  private maxReconnectAttempts = 5;

  constructor() {
    this.initializeConnection();
  }

  private initializeConnection() {
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS(`${environment.apiBaseUrl}/ws`),
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: () => {
        this.reconnectAttempts = 0;
        this.connectionStatus.next(true);
        console.log('WebSocket connected');
      },
      onStompError: (error) => {
        this.connectionStatus.next(false);
        console.error('WebSocket error:', error);
        this.handleReconnection();
      }
    });

    this.stompClient.activate();
  }

  private handleReconnection() {
    if (this.reconnectAttempts < this.maxReconnectAttempts) {
      this.reconnectAttempts++;
      setTimeout(() => {
        console.log(`Reconnection attempt ${this.reconnectAttempts}`);
        this.stompClient.deactivate().then(() => this.stompClient.activate());
      }, 5000);
    }
  }

  public subscribeToBinUpdates(): Observable<SmartBin> {
    return new Observable<SmartBin>(observer => {
      if (this.stompClient.connected) {
        const subscription = this.stompClient.subscribe(
          '/topic/bins',
          (message: Message) => {
            const bin: SmartBin = JSON.parse(message.body);
            observer.next(bin);
          }
        );
        return () => subscription.unsubscribe();
      }
      
      const statusSub = this.connectionStatus.subscribe(connected => {
        if (connected) {
          const subscription = this.stompClient.subscribe(
            '/topic/bins',
            (message: Message) => {
              const bin: SmartBin = JSON.parse(message.body);
              observer.next(bin);
            }
          );
          statusSub.unsubscribe();
          return () => subscription.unsubscribe();
        }
        return undefined;
      });
      
      return () => statusSub.unsubscribe();
    });
  }

  public getConnectionStatus(): Observable<boolean> {
    return this.connectionStatus.asObservable();
  }

  public closeConnection() {
    if (this.stompClient.connected) {
      this.stompClient.deactivate();
      this.connectionStatus.next(false);
    }
  }
}
// import { Injectable } from '@angular/core';
// import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
// import { environment } from '../environments/environment';
// import { SmartBin } from '../models/SmartBin';

// @Injectable({
//   providedIn: 'root'
// })
// export class WebSocketService {
//   private socket$: WebSocketSubject<any>;

//   constructor() {
//     this.socket$ = webSocket(`${environment.wsUrl}/bins`);
//   }

//   getBinUpdates() {
//     return this.socket$.asObservable();
//   }

//   close() {
//     this.socket$.complete();
//   }
// }