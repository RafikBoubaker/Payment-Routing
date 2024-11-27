export interface Message {
    id: number;
    content: string;
    queueName: string;
    receivedAt: Date;
  }