import { Component, OnDestroy, OnInit } from '@angular/core';
import { BinService } from '../../service/bin-service.service';
import { WebSocketService } from '../../service/web-socket.service';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';
import { SmartBin } from '../../models/SmartBin';
import { HttpClientModule } from '@angular/common/http';





@Component({
  selector: 'app-dashboard',
  standalone:true,
  imports: [CommonModule,HttpClientModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})

export class DashboardComponent implements OnInit, OnDestroy {

  bins: SmartBin[] = [];
  selectedBin: SmartBin | null = null;
  private binUpdateSubscription!: Subscription;
  

  constructor(
    private binService: BinService,
    private wsService: WebSocketService
  ) {}


  ngOnInit(): void {
    this.loadBins();
    this.subscribeToUpdates();
  }

  ngOnDestroy(): void {
    if (this.binUpdateSubscription) {
      this.binUpdateSubscription.unsubscribe();
    }
    this.wsService.close();
  }

  loadBins(): void {
    this.binService.getAllBins().subscribe({
      next: (bins) => {
        this.bins = bins;
      },
      error: (error) => {
        console.error('Error loading bins', error);
      }
    });
  }

  subscribeToUpdates(): void {
    this.binUpdateSubscription = this.wsService.getBinUpdates().subscribe({
      next: (update) => {
        const index = this.bins.findIndex(bin => bin.id === update.id);
        if (index !== -1) {
          this.bins[index] = { ...this.bins[index], ...update };
        }
      },
      error: (error) => {
        console.error('WebSocket error', error);
        // Attempt to reconnect
        setTimeout(() => this.subscribeToUpdates(), 5000);
      }
    });
  }


  filterBinsByStatus(status: string) {
    // Add implementation here, for example:
    return this.bins.filter(bin => bin.status === status);
  }

  selectBin(bin: SmartBin): void {
    this.selectedBin = bin;
  }


  refreshData() {

    // this.fetchBinData();
  }
  getStatusClass(status: SmartBin['status']): string {
    switch (status) {
      case 'NORMAL': return 'status-normal';
      case 'WARNING': return 'status-warning';
      case 'FULL': return 'status-full';
      default: return '';
    }
  }
}
