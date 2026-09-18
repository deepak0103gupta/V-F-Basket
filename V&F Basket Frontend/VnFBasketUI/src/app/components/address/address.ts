import { Component, inject, signal } from '@angular/core';
import { UserService } from '../../services/user-profile';
import { Address } from '../../models/address.model';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';


@Component({
  selector: 'app-address',
  imports: [
    MatIconModule,
    CommonModule,
    RouterModule
  ],
  templateUrl: './address.html',
  styleUrl: './address.css',
})
export class UserAddress {

  getAllAddress = signal<Address[]>([]);

  ngOnInit(): void {
    this.getAllAddresses();
    this.addNewAddress();
    if (!this.userService.userProfile()) {
      this.userService.loadUserProfile();
    }

  }

  userService = inject(UserService);

  getAllAddresses() {
    this.userService.getAllAddresses().subscribe(
      (response) => {
        this.getAllAddress.set(response);
        console.log('All addresses retrieved successfully', response);
      });
  }

  addNewAddress() {
    console.log('Navigate to add address page');
    // this.router.navigate(['/add-address']);
  }

  editAddress(address: Address) {
    console.log('Edit address:', address);
    // this.router.navigate(['/edit-address', address.id]);
  }

  deleteAddress(addressId: number) {

  if (!addressId) return;

  if (confirm('Are you sure you want to delete this address?')) {

    this.userService.deleteAddress(addressId).subscribe({
      next: () => {
        console.log('Deleted address ID:', addressId);

        // Remove from UI instantly (better UX)
        this.getAllAddress.update(addresses =>
          addresses.filter(a => a.addressId !== addressId)
        );
      },
      error: (err) => {
        console.error('Delete failed', err);
      }
    });

  }
}


}
