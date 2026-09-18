import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';
import { Address } from '../models/address.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {

  private apiUrl: String = 'http://localhost:8080/vnfbasket';

  private http = inject(HttpClient);

  userProfile = signal<User | null>(null);

  getUserProfile(): Observable<User> {
    return this.http.get<User>(this.apiUrl + '/getUser');
  }

  loadUserProfile() {
    this.getUserProfile().subscribe(user => {
      this.userProfile.set(user);
    });
  }

  getDefaultAddress(): Observable<Address> {
    return this.http.get<Address>(this.apiUrl + '/getDefaultAddressByUserId',
      {
        params: { isDefault: 1 }
      }
    );
  }

  getAllAddresses(): Observable<Address[]> {
    return this.http.get<Address[]>(this.apiUrl + '/getAllAddressesByUserID');
  }

  deleteAddress(addressId: number):Observable<void> {
    return this.http.delete<void>(this.apiUrl + '/deleteAddress/' + addressId);
  }



}
