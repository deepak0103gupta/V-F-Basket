import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Product } from '../models/product.model';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private apiUrl: string = 'http://localhost:8080/vnfbasket';
  private http = inject(HttpClient);
   private searchText = new BehaviorSubject<string>('');
  searchText$ = this.searchText.asObservable();

  setSearchText(text: string) {
    this.searchText.next(text);
  }

  addProducts(productformData: FormData): Observable<Product>{
    return this.http.post<Product>(this.apiUrl + '/addProduct', productformData);
  }

  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl + '/getAllProducts');
  }

  getProductsByCategory(category: string): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl + '/getProductsByCategoryName?categoryName=' + category);
  }
  
  getProductByName(productName: string): Observable<Product> {
    return this.http.get<Product>(this.apiUrl + '/getProductByProductName/' + productName);
  }

  getProductById(productId: number): Observable<Product> {
    return this.http.get<Product>(this.apiUrl + '/getProductsById/' + productId);
  }

  deleteProductById(productId: number): Observable<void> {
    return this.http.delete<void>(this.apiUrl+'/deleteProduct/'+productId);
  }

  updateProduct(formData: FormData) {
  return this.http.put(this.apiUrl+'/updateProduct', formData);
}
}
