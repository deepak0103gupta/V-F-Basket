import { Component, HostListener, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth-service.service';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../services/product-service';
import { P } from '@angular/cdk/keycodes';
import { Product } from '../../models/product.model';

@Component({
  selector: 'app-navbar',
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {

  isLoggedIn: boolean = false;
  role: string | null = null;
  isAccountOpen = false;
  searchText: string = '';
  

  constructor(private authService: AuthService, private router: Router, private productService: ProductService) {}

  ngOnInit(): void {
    // 🔥 Subscribe to login state
    this.authService.isLoggedIn$.subscribe(status => {
      this.isLoggedIn = status;
    });

    this.authService.role$.subscribe(role => {
      this.role = role;
    });

  
    
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  toggleAccountMenu(event: Event) {
  event.stopPropagation(); // prevent document click closing immediately
  this.isAccountOpen = !this.isAccountOpen;
}

@HostListener('document:click')
closeMenu() {
  this.isAccountOpen = false;
}

  searchProduct() {
    this.productService.setSearchText(this.searchText);
  }


}
