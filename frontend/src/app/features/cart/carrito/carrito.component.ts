import { Component, OnInit, OnDestroy , inject} from '@angular/core';
import { CarritoService, CarritoItem } from '../../../core/services/carrito.service';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-carrito',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './carrito.component.html',
  styleUrls: ['./carrito.component.css']
})

export class CarritoComponent implements OnInit, OnDestroy {

  private carritoService = inject(CarritoService);
  items: CarritoItem[] = [];
  subtotal: number = 0;
  private subscription?: Subscription;


  ngOnInit(): void {
    this.subscription = this.carritoService.items$.subscribe(items => {
      this.items = items;
      this.subtotal = this.carritoService.getSubtotal();
    });
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  incrementarCantidad(item: CarritoItem): void {
    this.carritoService.updateItemQuantity(item.varianteId, item.cantidad + 1);
  }

  decrementarCantidad(item: CarritoItem): void {
    if (item.cantidad > 1) {
      this.carritoService.updateItemQuantity(item.varianteId, item.cantidad - 1);
    }
  }

  eliminarItem(item: CarritoItem): void {
    this.carritoService.removeItem(item.varianteId);
  }

  vaciarCarrito(): void {
    this.carritoService.clearCart();
  }
}
