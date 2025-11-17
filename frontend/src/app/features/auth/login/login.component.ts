import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms'
import { Router } from '@angular/router';

import { AuthService } from '../../../core/services/auth.service';
import { inject, Injectable } from '@angular/core';


@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private authService = inject(AuthService);
  miForm!: FormGroup;
  private router = inject(Router);

  constructor(private fb: FormBuilder) {

  }

  ngOnInit() {
    if (this.isLoggedIn()) {
      this.router.navigate(['/']);
      return;
    }
    
    this.miForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  private isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  onSubmit() {
    if (this.miForm.invalid) {
      this.miForm.markAllAsTouched();
      return;
    }

    console.log("Intentando login con:", this.miForm.value);

    this.authService.login(this.miForm.value as any).subscribe({
      next: () => {
        this.router.navigate(['/'])
      },
      error: (err) => {
        console.log('ERROR BACKEND:', err);
      }
    });

  }
}
