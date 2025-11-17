import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../../shared/models/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private http = inject(HttpClient);
  private urlUsuarios = 'http://localhost:8080/api/usuarios';
  private urlNuevo = 'http://localhost:8080/api/usuarios'

  getUsuarios(): Observable<any[]> {
    return this.http.get<any[]>(this.urlUsuarios);
  }

  // Crear un Usuario
  createUsuario(u: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(this.urlNuevo, u);
  }

  // Obtener un usuario por ID
  getUsuarioById(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.urlUsuarios}/${id}`);
  }

  // Obtener el usuario actual
  getCurrentUsuario(): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.urlUsuarios}/me`);
  }

  // Actualizar un usuario
  updateUsuario(id: number, u: Partial<Usuario>): Observable<Usuario> {
    return this.http.patch<Usuario>(`${this.urlUsuarios}/${id}`, u);
  }

}
