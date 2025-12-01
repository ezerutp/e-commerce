import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CarritoService, CarritoItem } from '../../../core/services/carrito.service';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';

interface Producto {
  id: number;
  nombre: string;
  descripcion: string;
  marca: string;
  precio: number;
}

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  providers: [FormsModule],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  productos: Producto[] = [];
  productosFiltrados: Producto[] = [];
  busqueda: string = '';
  mostrar: { [key: number]: boolean } = {};
  cargando: boolean = false;
  errorCarga: string | null = null;

  private apiUrl = 'https://api.ezer.pe/api/productos';


  private preciosPorProducto: { [key: number]: number } = {
    1: 1299.99,
    2: 2299.99,
    3: 3149.99,
    4: 1079.99,
    5: 299.99,
    6: 1499.99,
  };

  constructor(
    private carritoService: CarritoService,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos(): void {
    this.cargando = true;
    this.errorCarga = null;

    const productosObservable: Observable<any[]> = this.http.get<any[]>(this.apiUrl);

    productosObservable.pipe(
      catchError(error => {
        console.error('Error al cargar productos:', error);
        this.errorCarga = 'Error al cargar los productos. Por favor, intente nuevamente.';
        this.cargando = false;
        return throwError(() => error);
      })
    ).subscribe({
      next: (productosApi) => {
        // Mapear los productos de la API y asignar los precios específicos
        this.productos = productosApi.map(productoApi => ({
          id: productoApi.id,
          nombre: productoApi.nombre,
          descripcion: productoApi.descripcion || '',
          marca: productoApi.marca || '',
          precio: this.preciosPorProducto[productoApi.id] || 0
        }));
        this.productosFiltrados = [...this.productos];
        this.cargando = false;
      },
      error: (error) => {
        console.error('Error en la carga de productos:', error);
        this.cargando = false;
      }
    });
  }

  buscar(): void {
    if (!this.busqueda.trim()) {
      this.productosFiltrados = [...this.productos];
    } else {
      this.productosFiltrados = this.productos.filter(producto =>
        producto.nombre.toLowerCase().includes(this.busqueda.toLowerCase()) ||
        producto.marca.toLowerCase().includes(this.busqueda.toLowerCase())
      );
    }
  }

  getPrecio(producto: Producto): number {
    return producto.precio;
  }

  agregarAlCarrito(producto: Producto): void {
    console.log('Intentando agregar al carrito:', producto.nombre);

    const nuevoItem: CarritoItem = {
      varianteId: producto.id,
      nombre: producto.nombre,
      precioUnitario: producto.precio,
      cantidad: 1
    };

    const itemsAntes = this.carritoService.getItems();
    console.log('Items antes de agregar:', itemsAntes.length);

    this.carritoService.addItem(nuevoItem);

    const itemsDespues = this.carritoService.getItems();
    console.log('Items después de agregar:', itemsDespues.length);

  }

  trackByProductoId(index: number, producto: Producto): number {
    return producto.id;
  }

  recargarProductos(): void {
    this.cargarProductos();
  }
}