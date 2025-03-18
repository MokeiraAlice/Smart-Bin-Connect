import { Component } from '@angular/core';
import { FormBuilder, FormGroup,FormsModule,ReactiveFormsModule,Validators } from '@angular/forms';
import { CollectionRequestService } from '../../service/collection-request.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-request-form',
  standalone:true,
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './request-form.component.html',
  styleUrl: './request-form.component.css'
})



export class RequestFormComponent {
  requestForm: FormGroup;
  isSubmitting = false;
  submitError = '';

  constructor(
    private fb: FormBuilder,
    private requestService: CollectionRequestService,
    private router: Router
  ) {
    this.requestForm = this.fb.group({
      address: ['', [Validators.required]],
      notes: [''],
      // In a real app, you would get location from a map component
      // or geocoding service
      latitude: [0],
      longitude: [0]
    });
  }

  onSubmit(): void {
    if (this.requestForm.valid) {
      this.isSubmitting = true;
      this.submitError = '';
      
      const formValue = this.requestForm.value;
      const request = {
        userId: 'current-user-id', // Get from auth service in real app
        address: formValue.address,
        location: {
          latitude: formValue.latitude,
          longitude: formValue.longitude
        },
        notes: formValue.notes
      };

      this.requestService.createRequest(request).subscribe({
        next: () => {
          this.router.navigate(['/requests']);
        },
        error: (error) => {
          this.isSubmitting = false;
          this.submitError = 'Failed to submit request. Please try again.';
          console.error('Request submission error', error);
        }
      });
    }
  }
}
