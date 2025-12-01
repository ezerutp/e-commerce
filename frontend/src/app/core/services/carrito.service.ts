import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, forkJoin } from 'rxjs';
import { map, tap } from 'rxjs/operators';

export interface CarritoItem {
  id?: number;
  carritoId?: number;
  varianteId: number;
  nombre: string;
  cantidad: number;
  precioUnitario: number;
  sku?: string;
}

@Injectable({
  providedIn: 'root'
})
export class CarritoService {
  private http = inject(HttpClient);

  private readonly STORAGE_KEY = 'carrito_items';
  private itemsSubject = new BehaviorSubject<CarritoItem[]>([]);
  public items$ = this.itemsSubject.asObservable();

  private UrlItems = 'https://api.ezer.pe/api/carrito-items';
  private UrlCarrito = 'https://api.ezer.pe/api/carritos';

  constructor() {
    this.cargarItemsDesdeLocalStorage();
  }

  private cargarItemsDesdeLocalStorage(): void {
    try {
      const itemsGuardados = localStorage.getItem(this.STORAGE_KEY);
      if (itemsGuardados) {
        const items: CarritoItem[] = JSON.parse(itemsGuardados);
        this.itemsSubject.next(items);
      } else {
        this.itemsSubject.next([]);
      }
    } catch (error) {
      console.error('Error al cargar items del localStorage:', error);
      this.itemsSubject.next([]);
    }
  }

  private guardarItemsEnLocalStorage(items: CarritoItem[]): void {
    try {
      localStorage.setItem(this.STORAGE_KEY, JSON.stringify(items));
    } catch (error) {
      console.error('Error al guardar items en localStorage:', error);
    }
  }

  getItems(): CarritoItem[] {
    return this.itemsSubject.value;
  }

  addItem(item: CarritoItem): void {
    const currentItems = this.itemsSubject.value;
    const existingItemIndex = currentItems.findIndex(existingItem => existingItem.varianteId === item.varianteId);

    let updatedItems: CarritoItem[];

    if (existingItemIndex !== -1) {
      const updatedItem = {
        ...currentItems[existingItemIndex],
        cantidad: currentItems[existingItemIndex].cantidad + item.cantidad
      };
      updatedItems = currentItems.map((currentItem, index) =>
        index === existingItemIndex ? updatedItem : currentItem
      );
    } else {
      updatedItems = [...currentItems, item];
    }

    this.itemsSubject.next(updatedItems);
    this.guardarItemsEnLocalStorage(updatedItems);
  }

  removeItem(varianteId: number): void {
    const currentItems = this.itemsSubject.value;
    const updatedItems = currentItems.filter(item => item.varianteId !== varianteId);

    this.itemsSubject.next(updatedItems);
    this.guardarItemsEnLocalStorage(updatedItems);
  }

  clearCart(): void {
    this.itemsSubject.next([]);
    localStorage.removeItem(this.STORAGE_KEY);
  }

  getSubtotal(): number {
    return this.itemsSubject.value.reduce((sum, item) => sum + (item.precioUnitario * item.cantidad), 0);
  }

  getTotalItems(): number {
    return this.itemsSubject.value.reduce((total, item) => total + item.cantidad, 0);
  }

  updateItemQuantity(varianteId: number, nuevaCantidad: number): void {
    const currentItems = this.itemsSubject.value;
    const existingItemIndex = currentItems.findIndex(item => item.varianteId === varianteId);

    if (existingItemIndex !== -1 && nuevaCantidad > 0) {
      const updatedItem = { ...currentItems[existingItemIndex], cantidad: nuevaCantidad };
      const updatedItems = currentItems.map((item, index) =>
        index === existingItemIndex ? updatedItem : item
      );
      this.itemsSubject.next(updatedItems);
      this.guardarItemsEnLocalStorage(updatedItems);
    }
  }

  // Método para cargar items desde el backend y reemplazar los items locales
  cargarItemsDesdeBackend(carritoId: number): Observable<CarritoItem[]> {
    return this.http.get<CarritoItem[]>(`${this.UrlItems}?carritoId=${carritoId}`).pipe(
      tap(items => {
        // Actualizar los items locales con los recibidos del backend
        this.itemsSubject.next(items);
        this.guardarItemsEnLocalStorage(items);
      })
    );
  }

  // Crear un carrito en el backend y sincronizar los items
  crearCarritoYGuardarItems(usuarioId: number): Observable<any> {
    const payload = {
      usuarioId,
      estado: 'CREADO',
      subtotal: this.getSubtotal(),
      total: this.getSubtotal(),
      moneda: 'USD'
    };

    return this.http.post(this.UrlCarrito, payload).pipe(
      map((carrito: any) => {
        // Una vez creado el carrito, guardar todos los items
        const requestsItems = this.itemsSubject.value.map(item => {
          const payloadItem = {
            carritoId: carrito.id,
            varianteId: item.varianteId,
            cantidad: item.cantidad,
            precioUnitario: item.precioUnitario
          };
          return this.http.post(this.UrlItems, payloadItem);
        });

        return forkJoin(requestsItems).pipe(
          map(() => carrito)
        );
      })
    );
  }

  // Guardar todos los items actuales en el backend para un carrito específico
  saveItemsBackend(carritoId: number): Observable<any[]> {
    const requests = this.itemsSubject.value.map(item => {
      const payload = {
        carritoId,
        varianteId: item.varianteId,
        cantidad: item.cantidad,
        precioUnitario: item.precioUnitario
      };
      return this.http.post(this.UrlItems, payload);
    });

    return forkJoin(requests);
  }

  // Método para sincronizar todos los items del localStorage con el backend
  sincronizarConBackend(carritoId: number): Observable<any[]> {
    return this.saveItemsBackend(carritoId);
  }

  // Métodos auxiliares para operaciones individuales en el backend
  fetchItemsBackend(carritoId: number): Observable<CarritoItem[]> {
    return this.http.get<CarritoItem[]>(`${this.UrlItems}?carritoId=${carritoId}`);
  }

  updateItemBackend(item: CarritoItem): Observable<CarritoItem> {
    if (!item.id) {
      throw new Error('El item debe tener un ID para ser actualizado');
    }
    return this.http.patch<CarritoItem>(`${this.UrlItems}/${item.id}`, item);
  }

  deleteItemBackend(id: number): Observable<void> {
    return this.http.delete<void>(`${this.UrlItems}/${id}`);
  }

  // Método para obtener un item específico del carrito local
  getItemByVarianteId(varianteId: number): CarritoItem | undefined {
    return this.itemsSubject.value.find(item => item.varianteId === varianteId);
  }

  // Verificar si existe un item en el carrito
  existeItem(varianteId: number): boolean {
    return this.itemsSubject.value.some(item => item.varianteId === varianteId);
  }
}