import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_PARTNERS } from '../../app.constants';
import { Partner } from '../models/partner.model';
import { Page } from '../models/page.model';


@Injectable({
  providedIn: 'root'
})
export class PartnerService {


  constructor(private http: HttpClient) {}

  getPartners(page: number, size: number): Observable<Page<Partner>> {
    return this.http.get<Page<Partner>>(`${SERVER_API_PARTNERS}?page=${page}&size=${size}`);
  }

  addPartner(partner: Partner): Observable<Partner> {
    return this.http.post<Partner>(SERVER_API_PARTNERS, partner);
  }

  updatePartner(partner: Partner): Observable<Partner> {
    return this.http.put<Partner>(`${SERVER_API_PARTNERS}/${partner.id}`, partner);
  }

  deletePartner(id: number): Observable<void> {
    return this.http.delete<void>(`${SERVER_API_PARTNERS}/${id}`);
  }
}