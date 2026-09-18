import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Product } from '../../models/product.model';
import { AuthService } from '../../services/auth-service.service';
import { ProductService } from '../../services/product-service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';


@Component({
  selector: 'app-product-card',
  standalone: true,
  imports: [CommonModule,],
  templateUrl: './product-card.html',
  styleUrl: './product-card.css',
})
export class ProductCard {
  @Input() product!: Product;
  role: string | null = null;
  @Output() productDeleted = new EventEmitter<number>();
  toastr = inject(ToastrService);

  constructor(private authService: AuthService, private productService: ProductService, private router: Router) {}

  ngOnInit(): void {
    this.authService.role$.subscribe(role => {
      this.role = role;
    });
  }

  addToCart() {
    console.log('Added to cart:', this.product.productName);
    // Backend API call will be added here
  }

  editProduct(event: Event) {
  event.stopPropagation();
  console.log('Edit clicked');

  this.router.navigate(['/edit-product', this.product.productId]);
}

deleteProduct(event: Event) {
  event.stopPropagation();
  
  const confirmDelete = confirm('Are you sure you want to delete this product?');
  if (confirmDelete) {
    this.productService.deleteProductById(this.product.productId).subscribe({
      next: () => {
        this.toastr.error('Product deleted successfully!');
        this.productDeleted.emit(this.product.productId);
      },
      error: (err) => {
        console.error('Delete failed', err);
      }
    });
  }
}



}
