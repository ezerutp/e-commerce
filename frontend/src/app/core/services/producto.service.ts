import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Producto } from '../../shared/models/producto';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  private http = inject(HttpClient);
  private urlProductos = 'http://localhost:8080/api/productos';
  private urlNuevo = 'http://localhost:8080/api/productos'

  getProductos(): Observable<any[]> {
    return this.http.get<any[]>(this.urlProductos);
  }

  // Crear un Producto
  createProducto(u: Producto): Observable<Producto> {
    return this.http.post<Producto>(this.urlNuevo, u);
  }

  // Obtener un producto por ID
  getProductoById(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.urlProductos}/${id}`);
  }

  // Actualizar un producto
  updateProducto(id: number, u: Partial<Producto>): Observable<Producto> {
    return this.http.patch<Producto>(`${this.urlProductos}/${id}`, u);
  }

  // Eliminar un producto
  deleteProducto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.urlProductos}/${id}`);
  }

}
