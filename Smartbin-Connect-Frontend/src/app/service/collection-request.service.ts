import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CollectionRequest } from '../models/CollectionRequestmodel';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})


export class CollectionRequestService {
  private apiUrl = `${environment.apiBaseUrl}/api/collection-requests`;

  constructor(private http: HttpClient) {}

  getAllRequests(): Observable<CollectionRequest[]> {
    return this.http.get<CollectionRequest[]>(this.apiUrl);
  }

  getRequestById(id: string): Observable<CollectionRequest> {
    return this.http.get<CollectionRequest>(`${this.apiUrl}/${id}`);
  }

  createRequest(request: Omit<CollectionRequest, 'id' | 'requestTime' | 'status'>): Observable<CollectionRequest> {
    return this.http.post<CollectionRequest>(this.apiUrl, request);
  }

  updateRequestStatus(id: string, status: CollectionRequest['status']): Observable<CollectionRequest> {
    return this.http.patch<CollectionRequest>(`${this.apiUrl}/${id}/status`, { status });
  }

  scheduleRequest(id: string, scheduledTime: Date): Observable<CollectionRequest> {
    return this.http.patch<CollectionRequest>(`${this.apiUrl}/${id}/schedule`, { scheduledTime });
  }
}
