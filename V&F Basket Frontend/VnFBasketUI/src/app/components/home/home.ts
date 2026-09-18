import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductCard } from '../product-card/product-card';
import { Product } from '../../models/product.model';
import { ProductService } from '../../services/product-service';
import { ActivatedRoute } from '@angular/router';
import { timeout } from 'rxjs';

@Component({
  selector: 'app-home',
  imports: [CommonModule, ProductCard],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  products = signal<Product[]>([]);
  SearchedProduct = signal<String>('');


  categoryName!: string;
  
  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
  ) {}
  

  ngOnInit() {

    this.getcategoryNameFromRoute();
    
    this.loadProducts();
    this.getProductByName();
    
  }

  

  getAllProducts() {
    this.productService.getAllProducts().subscribe((data) => {
      this.products.set(data);
      console.log('Products loaded:', this.products());
    });
  }

  getProductByCatrgoryName() {
    this.productService.getProductsByCategory(this.categoryName).subscribe((data) => {
      this.products.set(data);
      console.log(`Products loaded for category ${this.categoryName}:`, this.products());
    });
  }

  getcategoryNameFromRoute() {
    this.route.paramMap.subscribe((params) => {
      this.categoryName = params.get('categoryName')!;
      this.getProductByCatrgoryName();
      console.log('Category from route:', this.categoryName);
    });
  }

  getSearchedProductName(){
    this.productService.searchText$.subscribe((text) => {
      this.SearchedProduct.set(text);
      
    });
  }


  getProductByName(){
    this.productService.searchText$.subscribe((text) => {
      if (text) {
        this.productService.getProductByName(text).subscribe((data) => {
          this.products.set([data]);
          console.log(`Products loaded for search "${text}":`, this.products());
        });} else {
          this.getAllProducts();
        }
    })
  }


  loadProducts() {
    if (this.categoryName) {
      this.getProductByCatrgoryName();
    } else {
      this.getAllProducts();
    }
  }



  onProductDeleted(productId: number) {
  
  this.products.update(products =>
    products.filter(p => p.productId !== productId)
  );
}
}
