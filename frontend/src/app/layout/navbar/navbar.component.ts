import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { UsuarioService } from '../../core/services/usuario.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  isLoginedIn: boolean = false;
  userName: string = '';
  
  constructor(
    private usuarioService: UsuarioService
  ) {
    this.loadUserName();
   }

  isLoggedIn(): boolean {
    const token = localStorage.getItem('token');
    this.isLoginedIn = !!token;
    return this.isLoginedIn;
  }

  logout() {
    localStorage.removeItem('token');
    this.isLoginedIn = false;
  }

    private loadUserName(): void {
    if (this.isLoggedIn()) {
      this.usuarioService.getCurrentUsuario().subscribe({
        next: (usuario) => {
          this.userName = usuario.nombre || '';
        },
        error: (err) => {
          console.error('Error al obtener usuario:', err);
          this.userName = '';
        }
      });
    }
  }

  getUserName(): string {
    return this.userName;
    }
}
