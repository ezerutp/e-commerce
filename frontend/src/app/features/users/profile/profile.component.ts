import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../../core/services/usuario.service';
import { AuthService } from '../../../core/services/auth.service';
import { Usuario } from '../../../shared/models/usuario';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  private fb = inject(FormBuilder);
  private usuarioService = inject(UsuarioService);
  private authService = inject(AuthService);
  private router = inject(Router);

  profileForm!: FormGroup;
  usuario: Usuario | null = null;
  loading = false;
  successMessage = '';
  errorMessage = '';

  ngOnInit(): void {
    this.initForm();
    this.loadUserData();
  }

  initForm(): void {
    this.profileForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      username: [{value: '', disabled: true}],
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      telefono: [''],
    });
  }

  loadUserData(): void {
    this.loading = true;
    this.usuarioService.getCurrentUsuario().subscribe({
      next: (usuario) => {
        this.usuario = usuario;
        this.profileForm.patchValue({
          email: usuario.email,
          username: usuario.username,
          nombre: usuario.nombre,
          apellido: usuario.apellido,
          telefono: usuario.telefono || ''
        });
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar datos del usuario:', error);
        this.errorMessage = 'Error al cargar los datos del usuario';
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.profileForm.valid && this.usuario) {
      this.loading = true;
      this.successMessage = '';
      this.errorMessage = '';

      const updatedData: Partial<Usuario> = {
        email: this.profileForm.get('email')?.value,
        nombre: this.profileForm.get('nombre')?.value,
        apellido: this.profileForm.get('apellido')?.value,
        telefono: this.profileForm.get('telefono')?.value
      };

      this.usuarioService.updateUsuario(this.usuario.id!, updatedData).subscribe({
        next: (response) => {
          this.successMessage = 'Perfil actualizado correctamente';
          this.usuario = response;
          // Actualizar el nombre en el navbar
          this.authService.setUserName(response.nombre);
          this.loading = false;
          setTimeout(() => {
            this.successMessage = '';
          }, 3000);
        },
        error: (error) => {
          console.error('Error al actualizar el perfil:', error);
          this.errorMessage = 'Error al actualizar el perfil. Por favor, intenta de nuevo.';
          this.loading = false;
        }
      });
    } else {
      this.markFormGroupTouched(this.profileForm);
    }
  }

  markFormGroupTouched(formGroup: FormGroup): void {
    Object.keys(formGroup.controls).forEach(key => {
      const control = formGroup.get(key);
      control?.markAsTouched();
    });
  }

  isFieldInvalid(fieldName: string): boolean {
    const field = this.profileForm.get(fieldName);
    return !!(field && field.invalid && field.touched);
  }

  getErrorMessage(fieldName: string): string {
    const field = this.profileForm.get(fieldName);
    if (field?.hasError('required')) {
      return 'Este campo es requerido';
    }
    if (field?.hasError('email')) {
      return 'Email inválido';
    }
    return '';
  }
}
