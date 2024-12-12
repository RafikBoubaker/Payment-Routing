import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';

import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';

import { Message } from '../../core/models/message.model';
import { HttpClientModule, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { catchError, finalize } from 'rxjs/operators';
import { of } from 'rxjs';
import { MessageService } from '../../core/services/messages.service';
import { MessageDialogComponent } from './message-dialog/message-dialog.component';

@Component({
  selector: 'app-message-list',
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule,
    MatCardModule,
    MatDividerModule,
    MatTableModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [
    MessageService
  ],
  templateUrl: './message-list.component.html',
  styleUrl: './message-list.component.scss',
})
export class MessageListComponent implements OnInit {
  displayedColumns = [
    'id',
    'content', 
    'queueName', 
    'receivedAt', 
    'action'
  ];
  dataSource = new MatTableDataSource<Message>([]);

  isLoadingResults: boolean = true;
  resultsLength: number = 0;
  currentPage: number = 0;
  pageSize: number = 10; 
  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;

  constructor(
    private dialog: MatDialog,
    private _messageService: MessageService 
  ) {}

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.loadMessages();
  }

  handlePage(e: any) {
    this.currentPage = e.pageIndex;
    this.loadMessages();
  }

  loadMessages() {
    this.isLoadingResults = true;
    
    this._messageService.getMessages(this.currentPage, this.pageSize)
      .pipe(
        catchError(error => {
          console.error('Erreur lors de la récupération des messages', error);
          
          return of({ content: [], totalElements: 0 });
        }),
        finalize(() => {
          this.isLoadingResults = false;
        })
      )
      .subscribe(page => {
        this.dataSource = new MatTableDataSource<Message>(page.content);
        this.resultsLength = page.totalElements;
      });
  }

  viewMessageDetails(message: Message) {

    this.dialog.open(MessageDialogComponent, {
      width: '60%',
      data: message
    });
  }
}
