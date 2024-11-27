export interface Partner {
    id: number;
    alias: string;
    type: string;
    direction: 'INBOUND' | 'OUTBOUND';
    application: string;
    processedFlowType: 'MESSAGE' | 'ALERTING' | 'NOTIFICATION';
    description: string;
  }