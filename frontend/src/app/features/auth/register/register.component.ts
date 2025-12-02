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
  usuarioEditando: Usuario | null = null;
  esEdicion: boolean = false;
  
  constructor(private fb: FormBuilder) {}
  
  ngOnInit() {
    this.miForm = this.fb.group({
    username: ['', [Validators.required, Validators.maxLength(50)]],
    password: ['', [Validators.maxLength(255)]],
    nombre: ['', [Validators.required, Validators.maxLength(120)]],
    apellido: ['', [Validators.required, Validators.maxLength(120)]],
    email: ['', [Validators.required, Validators.email, Validators.maxLength(255)]],
    telefono: ['', [Validators.required, Validators.pattern(/^[0-9]{9}$/)]]
    });
    this.cargarUsuarios();
    this.configurarModalListener();
  }

  configurarModalListener() {
    const modalElement = document.getElementById('registroModal');
    if (modalElement) {
      modalElement.addEventListener('hidden.bs.modal', () => {
        this.resetearFormulario();
      });
    }
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

    if (this.esEdicion && this.usuarioEditando && this.usuarioEditando.id) {
      // Modo edicion
      const usuarioData: Partial<Usuario> = {
        ...this.miForm.value,
        estado: this.usuarioEditando.estado ?? 'ACTIVO',
        rol: this.usuarioEditando.rol ?? 'USUARIO'
      };

      if (!usuarioData.password) {
        delete usuarioData.password;
      }

      this.usuarioService.updateUsuario(this.usuarioEditando.id, usuarioData).subscribe({
        next: (data) => {
          console.log('Usuario actualizado:', data);
          this.mostrarToast();
          this.cerrarModal();
          this.resetearFormulario();
          this.cargarUsuarios();
        },
        error: (err) => {
          console.error('Error al actualizar usuario:', err);
          this.mostrarToastError('Error al actualizar usuario. Por favor, intenta nuevamente.');
        }
      });
    } else {
      // Modo creación
      const usuarioData = {
        ...this.miForm.value,
        estado: 'ACTIVO',
        rol: 'USUARIO'
      };

      console.log("Enviando usuario:", usuarioData);

      this.usuarioService.createUsuario(usuarioData).subscribe({
        next: (data) => {
          console.log('Usuario creado:', data);
          this.mostrarToast();
          this.cerrarModal();
          this.resetearFormulario();
          this.cargarUsuarios();
        },
        error: (err) => {
          console.error('Error al crear usuario:', err);
          const mensaje = 'Error al crear usuario. Por favor, intenta nuevamente.';
          this.mostrarToastError(mensaje);
        },
      });
    }
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

  resetearFormulario() {
    this.miForm.reset();
    this.esEdicion = false;
    this.usuarioEditando = null;
  }

  editarUsuario(usuario: Usuario) {
    this.esEdicion = true;
    this.usuarioEditando = usuario;
    
    this.miForm.patchValue({
      username: usuario.username,
      password: '', // No mostrar la contraseña
      nombre: usuario.nombre,
      apellido: usuario.apellido,
      email: usuario.email,
      telefono: usuario.telefono
    });

    // Abrir modal
    const modalElement = document.getElementById('registroModal');
    if (modalElement) {
      const modal = new bootstrap.Modal(modalElement);
      modal.show();
    }
  }

  eliminarUsuario(id: number | undefined) {
    if (!id) {
      this.mostrarToastError('ID de usuario inválido');
      return;
    }

    if (confirm('¿Estás seguro de que deseas eliminar este usuario?')) {
      this.usuarioService.deleteUsuario(id).subscribe({
        next: () => {
          console.log('Usuario eliminado');
          this.mostrarToast();
          this.cargarUsuarios();
        },
        error: (err) => {
          console.error('Error al eliminar usuario:', err);
          this.mostrarToastError('Error al eliminar usuario. Por favor, intenta nuevamente.');
        }
      });
    }
  }
}
