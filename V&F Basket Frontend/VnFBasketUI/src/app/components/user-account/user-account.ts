import { Component, signal } from '@angular/core';
import { User } from '../../models/user.model';
import { UserService } from '../../services/user-profile';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { Router, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Address } from '../../models/address.model';

@Component({
  selector: 'app-user-account',
  imports: [
    MatIconModule,
    MatDividerModule,
    MatCardModule,
    MatButtonModule,
    RouterModule,
    ReactiveFormsModule
  ],
  templateUrl: './user-account.html',
  styleUrl: './user-account.css',
})
export class UserProfile {

  isProfileOpen = true;
  isEditMode = false;
  profileForm: FormGroup;
  defaultAddress = signal<Address | null>(null);

  constructor(public userService: UserService, private router: Router, private fb: FormBuilder) {
    this.profileForm = this.fb.group({
      FirstName: [''],
      Surname: [''],
      Mobile: [''],
      Email: ['']
    });

  }

  ngOnInit(): void {
    this.getUserProfile();
    this.getDefaultAddressDetails();
    
  }

  getUserProfile() {
   this.userService.loadUserProfile();
  }

  editProfile() {
    this.isEditMode = true;

    const user = this.userService.userProfile();
    if (user) {
      this.profileForm.patchValue({
        FirstName: user.firstName,
        Surname: user.lastName,
        Mobile: user.phoneNumber,
        Email: user.email
      });
    }
  }

  cancelEdit() {
    this.isEditMode = false;
  }

  getDefaultAddressDetails(){
    this.userService.getDefaultAddress().subscribe(
      (response) => {
        this.defaultAddress.set(response);
        console.log('Default address retrieved successfully', response);
        
      });
  }

}
