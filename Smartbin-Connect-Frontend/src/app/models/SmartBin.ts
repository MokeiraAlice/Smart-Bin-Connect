export interface SmartBin {
    id: string;
    location: {
      latitude: number;
      longitude: number;
      address: string;
    };
    fillLevel: number;
    lastUpdated: Date;
    status: 'NORMAL' | 'WARNING' | 'FULL';
    binType: 'GENERAL' | 'RECYCLABLE' | 'ORGANIC';
  }

  