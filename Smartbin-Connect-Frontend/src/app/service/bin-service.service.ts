import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SmartBin } from '../models/SmartBin';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})

export class BinService {
  private apiUrl = `${environment.apiBaseUrl}/api/bins`;

  constructor(private http: HttpClient) {}

  getAllBins(): Observable<SmartBin[]> {
    return this.http.get<SmartBin[]>(this.apiUrl);
  }

  getBinById(id: string): Observable<SmartBin> {
    return this.http.get<SmartBin>(`${this.apiUrl}/${id}`);
  }

  getBinsByFillLevelThreshold(threshold: number): Observable<SmartBin[]> {
    return this.http.get<SmartBin[]>(`${this.apiUrl}/fill-level?threshold=${threshold}`);
  }

  updateBinStatus(id: string, status: SmartBin['status']): Observable<SmartBin> {
    return this.http.patch<SmartBin>(`${this.apiUrl}/${id}/status`, { status });
  }
}