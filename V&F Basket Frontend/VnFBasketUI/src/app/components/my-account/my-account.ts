import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-my-account',
  imports: [
    RouterModule, 
    MatIconModule, 
    CommonModule
  ],
  templateUrl: './my-account.html',
  styleUrl: './my-account.css',
})
export class MyAccount {
}
