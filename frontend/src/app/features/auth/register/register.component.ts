import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms'

import { UsuarioService } from '../../../core/services/usuario.service';
import { inject, Injectable } from '@angular/core';
import { Usuario } from '../../../shared/models/usuario';

declare var bootstrap: any;

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})

export class RegisterComponent implements OnInit{

  private usuarioService = inject(UsuarioService);

  miForm!: FormGroup;
  usuarios: Usuario[] = [];
  
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
    this.cargarUsuarios();
  }

  cargarUsuarios() {
    this.usuarioService.getUsuarios().subscribe({
      next: (data) => {
        this.usuarios = data;
        console.log('Usuarios cargados:', this.usuarios);
      },
      error: (err) => console.error('Error al cargar usuarios:', err)
    });
  }
  
  onSubmit() { 
    if (this.miForm.invalid) {
      this.miForm.markAllAsTouched();
      return;
    }

    const usuarioData = {
      ...this.miForm.value,
      estado: 'ACTIVO',
      rol: 'USUARIO'
    };

    console.log("Enviando usuario:", usuarioData);

    this.usuarioService.createUsuario(usuarioData).subscribe({
      next: (data) => {
        console.log('Usuario creado:', data);
        
        // Mostrar toast de éxito
        this.mostrarToast();
        
        // Cerrar modal
        this.cerrarModal();
        
        // Resetear formulario
        this.miForm.reset();
        
        // Recargar lista de usuarios
        this.cargarUsuarios();
      },
      error: (err) => {
        console.error('Error al crear usuario:', err);
        const mensaje = 'Error al crear usuario. Por favor, intenta nuevamente.';
        this.mostrarToastError(mensaje);
      },
    });
  }

  mostrarToast() {
    const toastElement = document.getElementById('successToast');
    if (toastElement) {
      const toast = new bootstrap.Toast(toastElement);
      toast.show();
    }
  }

  mostrarToastError(mensaje: string) {
    const messageElement = document.getElementById('errorMessage');
    if (messageElement) {
      messageElement.textContent = mensaje;
    }
    const toastElement = document.getElementById('errorToast');
    if (toastElement) {
      const toast = new bootstrap.Toast(toastElement);
      toast.show();
    }
  }

  cerrarModal() {
    const modalElement = document.getElementById('registroModal');
    if (modalElement) {
      const modal = bootstrap.Modal.getInstance(modalElement);
      if (modal) {
        modal.hide();
      }
    }
  }
}
