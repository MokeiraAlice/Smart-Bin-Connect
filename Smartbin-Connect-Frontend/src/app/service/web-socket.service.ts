import { Injectable } from '@angular/core';
import SockJS from 'sockjs-client';
import { Client, IMessage } from '@stomp/stompjs';
import { Observable, Subject } from 'rxjs';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private client: Client;
  private binUpdatesSubject = new Subject<any>();

  constructor() {
    this.client = new Client({
      webSocketFactory: () => new SockJS(`${environment.apiBaseUrl}/ws`),
      debug: function(str) {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000
    });

    this.client.onConnect = (frame) => {
      console.log('Connected: ' + frame);
      this.client.subscribe('/topic/bin-updates', (message) => {
        if (message.body) {
          const update = JSON.parse(message.body);
          this.binUpdatesSubject.next(update);
        }
      });
    };

    this.client.onStompError = (frame) => {
      console.error('Broker reported error: ' + frame.headers['message']);
      console.error('Additional details: ' + frame.body);
    };

    this.client.activate();
  }

  getBinUpdates(): Observable<any> {
    return this.binUpdatesSubject.asObservable();
  }

  close() {
    if (this.client) {
      this.client.deactivate();
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