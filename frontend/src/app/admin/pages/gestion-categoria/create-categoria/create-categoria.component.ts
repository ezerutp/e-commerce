import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CategoriaService } from '../../../../core/services/categoria.service';

@Component({
  selector: 'app-categoria-create',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './create-categoria.component.html',
  styleUrls: ['./create-categoria.component.css']
})
export class CreateCategoriaComponent implements OnInit {

  private categoriaService = inject(CategoriaService);
  miForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    this.miForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.maxLength(255)]],
      descripcion: ['', [Validators.maxLength(500)]],
      activo: [true],
      slug: ['', [Validators.pattern(/^[a-z0-9]+(?:-[a-z0-9]+)*$/), Validators.maxLength(255)]],
    });
  }

  onSubmit() {
    if (this.miForm.invalid) {
      this.miForm.markAllAsTouched();
      return;
    }

    console.log("Enviando categoría:", this.miForm.value);

    this.categoriaService.createCategoria(this.miForm.value).subscribe({
      next: (data) => console.log('Categoría creada:', data),
      error: (err) => console.error('Error al crear categoría:', err),
    });
  }
}
