import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms'

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

  constructor(private fb: FormBuilder) { }

  ngOnInit() {
    this.miForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  onSubmit() {
    if (this.miForm.invalid) {
      this.miForm.markAllAsTouched();
      return;
    }

    console.log("Intentando login con:", this.miForm.value);

    this.authService.login(this.miForm.value).subscribe({
      next: (token) => {
        console.log('Token recibido:', token);
        localStorage.setItem('token', token);
      },
      error: (err) => console.error('Error en login:', err)
    });

  }
}
