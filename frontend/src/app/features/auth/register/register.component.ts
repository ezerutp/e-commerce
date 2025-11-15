import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms'

import { UsuarioService } from '../../../core/services/usuario.service';
import { inject, Injectable } from '@angular/core';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})

export class RegisterComponent implements OnInit{

  private usuarioService = inject(UsuarioService);

  miForm!: FormGroup;
  constructor(private fb: FormBuilder) {}
  
  ngOnInit() {
    this.miForm = this.fb.group({
    username: ['', [Validators.required, Validators.maxLength(50)]],
    password: ['', [Validators.required, Validators.maxLength(255)]],
    nombre: ['', [Validators.required, Validators.maxLength(120)]],
    apellido: ['', [Validators.required, Validators.maxLength(120)]],
    email: ['', [Validators.required, Validators.email, Validators.maxLength(255)]],
    telefono: ['', [Validators.required, Validators.pattern(/^[0-9]{9}$/)]]
  });
  }
  onSubmit() { 
    if (this.miForm.invalid) {
      this.miForm.markAllAsTouched();
      return;
    }

    console.log("Enviando usuario:", this.miForm.value);

    this.usuarioService.createUsuario(this.miForm.value).subscribe({
      next: (data) => console.log('Usuario creado:', data),
      error: (err) => console.error('Error al crear usuario:', err),
    });
  }
}
