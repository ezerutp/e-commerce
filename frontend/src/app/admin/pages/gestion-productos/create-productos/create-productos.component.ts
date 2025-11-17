import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms'

import { ProductoService } from '../../../../core/services/producto.service';
import { inject, Injectable } from '@angular/core';
import { Producto } from '../../../../shared/models/producto';

declare var bootstrap: any;

@Component({
  selector: 'app-producto-create',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './create-productos.component.html',
  styleUrls: ['./create-productos.component.css']
})
export class CreateProductosComponent implements OnInit {

  private productoService = inject(ProductoService);
  miForm!: FormGroup;
  productos: Producto[] = [];

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    this.miForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.maxLength(255)]],
      descripcion: [''],
      marca: ['', [Validators.maxLength(120)]],
      activo: [true],
      slug: ['', [Validators.pattern(/^[a-z0-9]+(?:-[a-z0-9]+)*$/), Validators.maxLength(255)]],
    });
    this.cargarProductos();
  }

  cargarProductos() {
    this.productoService.getProductos().subscribe({
      next: (data) => {
        this.productos = data;
        console.log('Productos cargados:', this.productos);
      },
      error: (err) => console.error('Error al cargar productos:', err)
    });
  }

  onSubmit() {
    if (this.miForm.invalid) {
      this.miForm.markAllAsTouched();
      return;
    }

    console.log("Enviando producto:", this.miForm.value);

    this.productoService.createProducto(this.miForm.value).subscribe({
      next: (data) => {
        console.log('Producto creado:', data);
        
        // Mostrar toast de éxito
        this.mostrarToast();
        
        // Cerrar modal
        this.cerrarModal();
        
        // Resetear formulario
        this.miForm.reset({ activo: true });
        
        // Recargar lista de productos
        this.cargarProductos();
      },
      error: (err) => console.error('Error al crear producto:', err),
    });
  }

  mostrarToast() {
    const toastElement = document.getElementById('successToast');
    if (toastElement) {
      const toast = new bootstrap.Toast(toastElement);
      toast.show();
    }
  }

  cerrarModal() {
    const modalElement = document.getElementById('productoModal');
    if (modalElement) {
      const modal = bootstrap.Modal.getInstance(modalElement);
      if (modal) {
        modal.hide();
      }
    }
  }
}
