import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from '../models/category.model';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private apiUrl: string = 'http://localhost:8080/vnfbasket';
  private http = inject(HttpClient);
  
  getAllCategories(): Observable<Category[]> {    
    return this.http.get<Category[]>(this.apiUrl+"/getAllCategories");
  }

}
