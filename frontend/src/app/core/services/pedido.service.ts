import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, forkJoin } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { CarritoItem } from './carrito.service';

export interface Pedido {
  id: number;
  numeroOrden: string;
  usuarioId: number;
  estado: string;
  subtotal: number;
  total: number;
  moneda: string;
}

@Injectable({
  providedIn: 'root'
})
export class PedidoService {
  private http = inject(HttpClient);
  private urlPedidos = 'http://api.ezer.pe/api/pedidos';
  private urlPedidoItems = 'http://api.ezer.pe/api/pedido-items';

  crearPedidoCompleto(items: CarritoItem[], usuarioId: number): Observable<Pedido> {
    const subtotal = items.reduce((sum, item) => sum + (item.precioUnitario * item.cantidad), 0);
    
    // Preparar los datos del pedido
    const pedidoData = {
      usuarioId: usuarioId,
      numeroOrden: this.generarNumeroOrden(),
      estado: 'PENDIENTE',
      subtotal: subtotal,
      total: subtotal,
      moneda: 'USD'
    };

    // Primero crear el pedido
    return this.http.post<Pedido>(this.urlPedidos, pedidoData).pipe(
      switchMap((pedidoCreado: Pedido) => {
        // Preparar y crear todos los ítems del pedido
        const requestsItems = items.map(item => {
          const pedidoItemData = {
            pedidoId: pedidoCreado.id,
            varianteId: item.varianteId,
            nombreProductoSnapshot: item.nombre,
            precioUnitario: item.precioUnitario,
            cantidad: item.cantidad
          };
          return this.http.post(this.urlPedidoItems, pedidoItemData);
        });

        // Esperar a que todos los ítems se creen correctamente
        return forkJoin(requestsItems).pipe(
          map(() => pedidoCreado)
        );
      })
    );
  }

  private generarNumeroOrden(): string {
    const ahora = new Date();
    const fecha = ahora.toISOString().slice(0, 10).replace(/-/g, '');
    const hora = ahora.getHours().toString().padStart(2, '0');
    const minutos = ahora.getMinutes().toString().padStart(2, '0');
    const segundos = ahora.getSeconds().toString().padStart(2, '0');
    const numeroAleatorio = Math.floor(Math.random() * 1000).toString().padStart(3, '0');
    
    return `PED-${fecha}${hora}${minutos}${segundos}${numeroAleatorio}`;
  }
}
