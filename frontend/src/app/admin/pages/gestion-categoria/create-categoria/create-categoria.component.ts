import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CategoriaService } from '../../../../core/services/categoria.service';
import { Categoria } from '../../../../shared/models/categoria';

declare var bootstrap: any;

@Component({
  selector: 'app-categoria-create',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './create-categoria.component.html',
  styleUrls: ['./create-categoria.component.css']
})
export class CreateCategoriaComponent implements OnInit {

  private categoriaService = inject(CategoriaService);
  miForm!: FormGroup;
  categorias: Categoria[] = [];

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    this.miForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.maxLength(255)]],
      descripcion: ['', [Validators.maxLength(500)]],
      activo: [true],
      slug: ['', [Validators.pattern(/^[a-z0-9]+(?:-[a-z0-9]+)*$/), Validators.maxLength(255)]],
    });
    this.cargarCategorias();
  }

  cargarCategorias() {
    this.categoriaService.getCategorias().subscribe({
      next: (data) => {
        this.categorias = data;
        console.log('Categorías cargadas:', this.categorias);
      },
      error: (err) => console.error('Error al cargar categorías:', err)
    });
  }

  onSubmit() {
    if (this.miForm.invalid) {
      this.miForm.markAllAsTouched();
      return;
    }

    console.log("Enviando categoría:", this.miForm.value);

    this.categoriaService.createCategoria(this.miForm.value).subscribe({
      next: (data) => {
        console.log('Categoría creada:', data);
        
        // Mostrar toast de éxito
        this.mostrarToast();
        
        // Cerrar modal
        this.cerrarModal();
        
        // Resetear formulario
        this.miForm.reset({ activo: true });
        
        // Recargar lista de categorías
        this.cargarCategorias();
      },
      error: (err) => console.error('Error al crear categoría:', err),
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
    const modalElement = document.getElementById('categoriaModal');
    if (modalElement) {
      const modal = bootstrap.Modal.getInstance(modalElement);
      if (modal) {
        modal.hide();
      }
    }
  }
}
