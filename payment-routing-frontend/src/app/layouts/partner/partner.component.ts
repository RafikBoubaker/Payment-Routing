import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialogModule, MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

import { PartnerService } from '../../core/services/partner.service';
import { Partner } from '../../core/models/partner.model';
import { PartnerDialogComponent } from './partner-dialog/partner-dialog.component';

import { catchError, finalize, of } from 'rxjs';

@Component({
  selector: 'app-partner',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatDividerModule,
    HttpClientModule,
    MatPaginatorModule,
    MatTableModule,
    MatProgressSpinnerModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [PartnerService],
  templateUrl: './partner.component.html',
  styleUrls: ['./partner.component.scss'],
})
export class PartnerComponent implements OnInit {
  displayedColumns = ['id', 'alias', 'type', 'direction', 'application', 'processedFlowType', 'description', 'action'];
  dataSource = new MatTableDataSource<Partner>([]);
  isLoadingResults = true;
  resultsLength = 0;
  currentPage = 0;
  pageSize = 10;
  deletePartnerDialogRef: MatDialogRef<any> | null = null;

  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;
  @ViewChild('deletePartnerModal', { static: true }) deletePartnerModal!: TemplateRef<any>;

  constructor(
    private dialog: MatDialog, 
    private _partnerService: PartnerService
  ) {}

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.loadPartners();
  }

  handlePage(event: any) {
    this.currentPage = event.pageIndex;
    this.loadPartners();
  }

  loadPartners() {
    this.isLoadingResults = true;
    this._partnerService
      .getPartners(this.currentPage, this.pageSize)
      .pipe(
        catchError((error) => {
          console.error('Erreur lors de la récupération des partenaires:', error);
          return of({ content: [], totalElements: 0 }); 
        }),
        finalize(() => {
          this.isLoadingResults = false;
        })
      )
      .subscribe((page) => {
        this.dataSource = new MatTableDataSource<Partner>(page.content);
        this.resultsLength = page.totalElements;
      });
  }

  openAddPartnerDialog() {
    const dialogRef = this.dialog.open(PartnerDialogComponent, { width: '40%' });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.loadPartners();
      }
    });
  }

  openEditPartnerDialog(partner: Partner) {
    const dialogRef = this.dialog.open(PartnerDialogComponent, {
      width: '40%',
      data: partner,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.loadPartners();
      }
    });
  }

  deletePartner(id: number) {

    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '400px';
    dialogConfig.data = { message: 'Êtes-vous sûr de vouloir supprimer ce partenaire ?' };


    this.deletePartnerDialogRef = this.dialog.open(this.deletePartnerModal, dialogConfig);

    this.deletePartnerDialogRef.afterClosed()
      .pipe(finalize(() => this.deletePartnerDialogRef = null))
      .subscribe(result => {
        if (result === true) {
   
          this._partnerService.deletePartner(id).subscribe(() => {
            this.loadPartners();
          });
        }
      });
  }
}