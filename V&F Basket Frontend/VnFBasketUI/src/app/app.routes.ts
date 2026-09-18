import { Routes } from '@angular/router';
import { Registration } from './components/registration/registration';
import { Login } from './components/login/login';
import { Home } from './components/home/home';
import { AddProduct } from './components/add-product/add-product';
import { UserProfile } from './components/user-account/user-account';
import { UserAddress } from './components/address/address';
import { MyAccount } from './components/my-account/my-account';

export const routes: Routes = [
  { path: '', component: Home},
  { path: 'login', component: Login },
  { path: 'registration', component: Registration },
  { path: 'add-product', component: AddProduct },
  { path: 'category/:categoryName', component: Home },
  {path: 'edit-product/:id',component: AddProduct},
  {
  path: 'account',
  component: MyAccount,
  children: [
    { path: 'profile', component: UserProfile },
    { path: 'addresses', component: UserAddress },
    { path: '', redirectTo: 'profile', pathMatch: 'full' }
  ]
}
];
