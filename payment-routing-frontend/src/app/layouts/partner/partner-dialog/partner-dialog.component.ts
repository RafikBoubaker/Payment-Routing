import { Component, OnInit, Inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import {
  MatDialogRef,
  MAT_DIALOG_DATA,
  MatDialogModule,
} from '@angular/material/dialog';

import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { PartnerService } from '../../../core/services/partner.service';
import { Partner } from '../../../core/models/partner.model';

@Component({
  selector: 'app-partner-dialog',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatDividerModule,
    MatProgressSpinnerModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  templateUrl: './partner-dialog.component.html',
  styleUrl: './partner-dialog.component.scss',
})
export class PartnerDialogComponent implements OnInit {
  partnerForm!: FormGroup;
  isEditMode = false;

  constructor(
    private formBuilder: FormBuilder,
    private dialogRef: MatDialogRef<PartnerDialogComponent>,
    private _partnerService: PartnerService,
    @Inject(MAT_DIALOG_DATA) public editData: Partner | null
  ) {}

  ngOnInit(): void {
    this.partnerForm = this.formBuilder.group({
      alias: ['', [Validators.required, Validators.minLength(2)]],
      type: ['', [Validators.required, Validators.minLength(2)]],
      direction: ['', Validators.required],
      application: [''],
      processedFlowType: ['', Validators.required],
      description: ['', [Validators.required, Validators.minLength(10)]]
    });

    if (this.editData) {
      this.isEditMode = true;
      this.loadEditData();
    }
  }

  loadEditData(): void {
    if (this.editData) {
      this.partnerForm.patchValue({
        alias: this.editData.alias,
        type: this.editData.type,
        direction: this.editData.direction,
        application: this.editData.application,
        processedFlowType: this.editData.processedFlowType,
        description: this.editData.description
      });
    }
  }

  savePartner(): void {
    if (this.partnerForm.valid) {
      const partnerData: Partner = this.partnerForm.value;

      if (this.isEditMode && this.editData?.id) {
       
        partnerData.id = this.editData.id;
        this._partnerService.updatePartner(partnerData).subscribe({
          next: (updatedPartner) => {
            this.dialogRef.close(updatedPartner);
          },
          error: (error) => {
            console.error('Erreur lors de la mise à jour du partenaire', error);
            
          }
        });
      } else {
        
        this._partnerService.addPartner(partnerData).subscribe({
          next: (newPartner) => {
            this.dialogRef.close(newPartner);
          },
          error: (error) => {
            console.error('Erreur lors de l\'ajout du partenaire', error);
           
          }
        });
      }
    }
  }
}
