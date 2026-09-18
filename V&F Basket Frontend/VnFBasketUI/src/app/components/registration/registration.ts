import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth-service.service';
import { map } from 'rxjs';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';

@Component({
  selector: 'app-registration',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    RouterLink,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatDividerModule
  ],
  templateUrl: './registration.html',
  styleUrl: './registration.css',
})
export class Registration implements OnInit {
  registrationForm:FormGroup;
  usernameExists=false;
  
  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.registrationForm = this.fb.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email], this.validateUsername.bind(this)],
      password: ['', [Validators.required, Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,16}$')]],
      confirmPassword: ['', [Validators.required]],
      phoneNumber: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]]
    })
   }
  ngOnInit(): void {}

  passwordMismatch(registrationForm: AbstractControl){

    return registrationForm.get('password')?.value === registrationForm.get('confirmPassword')?.value ? null : {mismatch : true};

  }

  register(){
    // Reset messages
    if(this.registrationForm.valid && this.registrationForm.value.password === this.registrationForm.value.confirmPassword){
      
      // Email doesn't exist, proceed with registration
      let userObject={
        firstName: this.registrationForm.value.firstName,
        lastName: this.registrationForm.value.lastName,
        email: this.registrationForm.value.email,
        password: this.registrationForm.value.password,
        phoneNumber: this.registrationForm.value.phoneNumber
      }
      
      console.log('Registering with:', userObject);
      
      this.authService.register(userObject).subscribe(
        response => {
          console.log('Registration successful', response);
          this.router.navigate(['/login']);
        },(error:any) => {
          console.error('Registration failed', error);
        });
    }
            
  }

  validateUsername(email: FormControl){
    const userName = email.value;
    return this.authService.checkEmailExists(userName).pipe(
      map(res => {
        if(res){
          return {userExists: true};
        }
        return null;
      })
    );
  }
}
