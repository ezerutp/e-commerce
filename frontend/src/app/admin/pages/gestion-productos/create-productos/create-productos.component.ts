import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms'

import { ProductoService } from '../../../../core/services/producto.service';
import { inject, Injectable } from '@angular/core';

@Component({
  selector: 'app-producto-create',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './create-productos.component.html',
  styleUrls: ['./create-productos.component.css']
})
export class CreateProductosComponent implements OnInit {

  private productoService = inject(ProductoService);
  miForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    this.miForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.maxLength(255)]],
      descripcion: [''],
      marca: ['', [Validators.maxLength(120)]],
      activo: [true],
      slug: ['', [Validators.pattern(/^[a-z0-9]+(?:-[a-z0-9]+)*$/), Validators.maxLength(255)]],
    });
  }

  onSubmit() {
    if (this.miForm.invalid) {
      this.miForm.markAllAsTouched();
      return;
    }

    console.log("Enviando producto:", this.miForm.value);

    this.productoService.createProducto(this.miForm.value).subscribe({
      next: (data) => console.log('Producto creado:', data),
      error: (err) => console.error('Error al crear producto:', err),
    });
  }
}
