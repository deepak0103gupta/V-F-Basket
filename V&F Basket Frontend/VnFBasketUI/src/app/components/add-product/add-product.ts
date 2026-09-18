import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProductService } from '../../services/product-service';
import { Category } from '../../models/category.model';
import { CategoryService } from '../../services/category-service';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-product',
  imports: [ReactiveFormsModule, CommonModule, ToastrModule],
  templateUrl: './add-product.html',
  styleUrl: './add-product.css',
})
export class AddProduct {
  productForm!: FormGroup;
  categories  = signal<Category[]>([]);
  selectedFile!: File;
  imagePreview: string | ArrayBuffer | null = null;
  toastr = inject(ToastrService);
  productId!: number;
  isEditMode = false;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.productForm = this.fb.group({
      productName: ['', [Validators.required]],
      productPrice: [0, [Validators.required, Validators.min(0)]],
      categoryId: [0, [Validators.required]],
      productDescription: ['', [Validators.required]],
      stockQuantity: [0, [Validators.required, Validators.min(0)]],
    });
  }

  ngOnInit() {
    this.loadCategories();

    this.route.paramMap.subscribe(params => {
    const id = params.get('id');

    if (id) {
      this.productId = +id;
      this.isEditMode = true;
      this.loadProductById();
    }
  });
  }

 


  saveProduct() {

  if (this.productForm.invalid) {
    alert("Please fill all fields.");
    return;
  }

  const formData = new FormData();

  // VERY IMPORTANT
  if (this.isEditMode) {
    formData.append('productId', this.productId.toString());
  }

  formData.append('productName', this.productForm.get('productName')?.value);
  formData.append('productPrice', this.productForm.get('productPrice')?.value);
  formData.append('categoryId', this.productForm.get('categoryId')?.value);
  formData.append('productDescription', this.productForm.get('productDescription')?.value);
  formData.append('stockQuantity', this.productForm.get('stockQuantity')?.value);

  if (this.selectedFile) {
    formData.append('productImage', this.selectedFile);
  }

  if (this.isEditMode) {

    this.productService.updateProduct(formData).subscribe(() => {
      this.toastr.success('Product updated successfully!');
      this.router.navigate(['/']);
    });

  } else {

    if (!this.selectedFile) {
      alert("Please select an image.");
      return;
    }

    this.productService.addProducts(formData).subscribe(() => {
      this.toastr.success('Product added successfully!');
      this.router.navigate(['/']);
    });
  }
}

  loadCategories() {
    this.categoryService.getAllCategories().subscribe((data) => {
      this.categories.set(data);
      console.log('Categories loaded:', this.categories());
    });
  }

  onFileSelected(event: any) {
  const file = event.target.files[0];

  if (!file) return;

  // Validate type
  if (file.type !== 'image/png' && file.type !== 'image/jpeg') {
    alert('Only PNG and JPEG images are allowed.');
    return;
  }

  this.selectedFile = file;

  const reader = new FileReader();
  reader.onload = () => {
    this.imagePreview = reader.result;
  };
  reader.readAsDataURL(file);
}

loadProductById() {
  this.productService.getProductById(this.productId).subscribe(product => {

        console.log('FULL PRODUCT:', product);
    console.log('product.categoryId:', product.categoryId, typeof product.categoryId);
    console.log('Categories list:', this.categories());

    this.productForm.patchValue({
      productName: product.productName,
      productPrice: product.productPrice,
      categoryId: product.categoryId,
      productDescription: product.productDescription,
      stockQuantity: product.stockQuantity
    });
    console.log('Form value after patch:', this.productForm.value);

    this.imagePreview = product.productImageUrl ?? null;
  });

}

}
