import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { SERVER_API_MESSAGES } from '../../app.constants';
import { Message } from '../models/message.model';
import { Page } from '../models/page.model';


@Injectable({
   providedIn: 'root'
})
export class MessageService {
 

  constructor(private _httpClient: HttpClient) {}

  getMessages(page: number, size: number): Observable<Page<Message>> {
    return this._httpClient.get<Page<Message>>(`${SERVER_API_MESSAGES}?page=${page}&size=${size}`);
  }

  getMessageDetails(id: number): Observable<Message> {
    return this._httpClient.get<Message>(`${SERVER_API_MESSAGES}/${id}`);
  }
}