import { Component, inject } from '@angular/core';
import { CarritoService, CarritoItem } from '../../core/services/carrito.service';
import { PedidoService } from '../../core/services/pedido.service';
import { UsuarioService } from '../../core/services/usuario.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
  imports: [CommonModule, FormsModule]
})
export class CheckoutComponent {

  private carritoService = inject(CarritoService);
  private pedidoService = inject(PedidoService);
  private usuarioService = inject(UsuarioService);
  private router = inject(Router);

  items: CarritoItem[] = [];
  subtotal: number = 0;
  estaProcesando = false;

  constructor() {
    this.cargarCarrito();
  }

  cargarCarrito() {
    this.items = this.carritoService.getItems();
    this.subtotal = this.carritoService.getSubtotal();
  }

  confirmarCompra(): void {
    if (this.items.length === 0) {
      alert('El carrito está vacío. No hay productos para procesar.');
      return;
    }

    const resultado = confirm(`¿Estás seguro de que deseas confirmar la compra por un total de $${this.subtotal.toFixed(2)}?`);

    if (resultado) {
      this.procesarCompra();
    }
  }

  private procesarCompra(): void {
    this.estaProcesando = true;

    // Obtener el usuario actual
    this.usuarioService.getCurrentUsuario().subscribe({
      next: (usuario) => {
        if (!usuario || !usuario.id) {
          alert('Error: No se pudo determinar el usuario actual.');
          this.estaProcesando = false;
          return;
        }

        // Una vez obtenido el usuario, proceder a crear el pedido
        this.pedidoService.crearPedidoCompleto(this.items, usuario.id).subscribe({
          next: (pedidoCreado) => {
            alert(`Compra realizada con éxito. Tu pedido ha sido creado con el número ${pedidoCreado.numeroOrden}`);
            
            this.carritoService.clearCart();
            this.cargarCarrito();
            this.router.navigate(['/listaProductos']);
          },
          error: (error) => {
            console.error('Error al procesar la compra:', error);
            alert('Error al procesar la compra. Por favor, intenta nuevamente.');
            this.estaProcesando = false;
          }
        });
      },
      error: (error) => {
        console.error('Error al obtener el usuario actual:', error);
        alert('Error al obtener la información del usuario. Por favor, verifica que estás autenticado.');
        this.estaProcesando = false;
      }
    });
  }

  regresarAlCarrito(): void {
    this.router.navigate(['/carrito']);
  }
}
