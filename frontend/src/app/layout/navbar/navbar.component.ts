import { Component, OnInit, OnDestroy, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { Subscription } from 'rxjs';

import { UsuarioService } from '../../core/services/usuario.service';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnDestroy {
  private usuarioService = inject(UsuarioService);
  private authService = inject(AuthService);
  private router = inject(Router);
  
  isLoginedIn: boolean = false;
  userName: string = '';
  private userNameSubscription?: Subscription;
  
  ngOnInit(): void {
    this.loadUserName();
    
    // Suscribirse a cambios en el nombre de usuario
    this.userNameSubscription = this.authService.userName$.subscribe(name => {
      this.userName = name;
    });
  }

  ngOnDestroy(): void {
    this.userNameSubscription?.unsubscribe();
  }

  isLoggedIn(): boolean {
    const token = localStorage.getItem('token');
    this.isLoginedIn = !!token;
    return this.isLoginedIn;
  }

  logout() {
    this.authService.logout();
    this.isLoginedIn = false;
    this.userName = '';
    this.router.navigate(['/login']);
  }

  private loadUserName(): void {
    if (this.isLoggedIn()) {
      this.usuarioService.getCurrentUsuario().subscribe({
        next: (usuario) => {
          this.userName = usuario.nombre || '';
          this.authService.setUserName(this.userName);
        },
        error: (err) => {
          console.error('Error al obtener usuario:', err);
          this.userName = '';
          this.authService.setUserName('');
        }
      });
    }
  }

  getUserName(): string {
    return this.userName;
  }
}
