
export interface CollectionRequest {
    id: string;
    userId: string;
    address: string;
    location: {
      latitude: number;
      longitude: number;
    };
    requestTime: Date;
    scheduledTime?: Date;
    status: 'PENDING' | 'SCHEDULED' | 'IN_PROGRESS' | 'COMPLETED' | 'CANCELLED';
    notes?: string;
  }