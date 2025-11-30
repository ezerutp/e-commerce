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
  productoEditar: Producto | null = null;

  constructor(private fb: FormBuilder) { }

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

  nuevoProducto() {
    this.productoEditar = null;
    this.miForm.reset({ activo: true });
    const modalElement = document.getElementById('productoModal');
    if (modalElement) {
      const modal = new bootstrap.Modal(modalElement);
      modal.show();
    }
  }

  editarProducto(producto: Producto) {
    this.productoEditar = producto;
    this.miForm.patchValue({
      nombre: producto.nombre,
      descripcion: producto.descripcion,
      marca: producto.marca,
      activo: producto.activo,
      slug: producto.slug
    });
    const modalElement = document.getElementById('productoModal');
    if (modalElement) {
      const modal = new bootstrap.Modal(modalElement);
      modal.show();
    }
  }

  onSubmit() {
    if (this.miForm.invalid) {
      this.miForm.markAllAsTouched();
      return;
    }
    console.log("Enviando producto:", this.miForm.value);
    if (this.productoEditar) {
      const updatedProducto = { ...this.productoEditar, ...this.miForm.value };
      this.productoService.updateProducto(updatedProducto.id, updatedProducto).subscribe({
        next: (data) => {
          this.mostrarToast('Producto actualizado exitosamente');
          this.cerrarModal();
          this.miForm.reset({ activo: true });
          this.productoEditar = null;
          this.cargarProductos();
        },
        error: (err) => console.error('Error al actualizar producto:', err)
      });
    } else {
      this.productoService.createProducto(this.miForm.value).subscribe({
        
        next: (data) => {
          console.log('Producto creado:', data);
          this.mostrarToast('Producto creado exitosamente');
          this.cerrarModal();
          this.miForm.reset({ activo: true });
          this.cargarProductos();
        },
        error: (err) => console.error('Error al crear producto:', err)
      });
    }
  }

  mostrarToast(mensaje: string) {
    const toastElement = document.getElementById('successToast');
    if (toastElement) {
      toastElement.querySelector('.toast-body')!.textContent = mensaje;
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
