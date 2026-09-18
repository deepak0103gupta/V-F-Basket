import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth-service.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    RouterLink,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatDividerModule,
  ],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login implements OnInit {

  loginForm:FormGroup;
  errorMsg:any;

  constructor(private fb:FormBuilder, private authService:AuthService, private router: Router) { 
    this.loginForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,16}$')]]
  });
  }

  ngOnInit(): void {
  }

  switchToRegistration(): void {
    this.router.navigate(['/registration']);
  }

  login(){
    if(this.loginForm.valid){
      const form = this.loginForm.value;
      const payload = { username: form.email, password: form.password };
      console.log('Login payload (sent):', payload);

      this.authService.login(payload).subscribe(resp => {
        console.log('Login response (full):', resp);
        console.log();
        const body = resp?.body ?? resp;
        this.authService.saveToken(body.token);
        this.authService.setRole(body.role);
        this.authService.setEmail(body.email);
        this.authService.setUserId(body.userId);
        this.router.navigate(['/']);
      }, (error:any) => {
        console.error('Login failed', error);
        this.errorMsg = error.error?.message || error.error || 'Login failed';
      });
    }
  }

}
