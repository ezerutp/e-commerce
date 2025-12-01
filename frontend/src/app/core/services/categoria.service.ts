import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Categoria } from '../../shared/models/categoria';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  private http = inject(HttpClient);
  private urlCategorias = 'https://api.ezer.pe/api/categorias';
  private urlNuevo = 'https://api.ezer.pe/api/categorias'

  getCategorias(): Observable<any[]> {
    return this.http.get<any[]>(this.urlCategorias);
  }

  // Crear una categoria
  createCategoria(u: Categoria): Observable<Categoria> {
    return this.http.post<Categoria>(this.urlNuevo, u);
  }

  // Obtener una categoria por ID
  getCategoriaById(id: number): Observable<Categoria> {
    return this.http.get<Categoria>(`${this.urlCategorias}/${id}`);
  }

  // Actualizar una categoria
  updateCategoria(id: number, u: Partial<Categoria>): Observable<Categoria> {
    return this.http.patch<Categoria>(`${this.urlCategorias}/${id}`, u);
  }

  // Eliminar una categoria
  deleteCategoria(id: number): Observable<void> {
    return this.http.delete<void>(`${this.urlCategorias}/${id}`);
  }

}
