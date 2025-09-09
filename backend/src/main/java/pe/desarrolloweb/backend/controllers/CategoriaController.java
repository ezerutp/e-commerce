package pe.desarrolloweb.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.desarrolloweb.backend.entities.Categoria;
import pe.desarrolloweb.backend.services.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    
	@Autowired
	private CategoriaService categoriaService;

	// Obtener todas las categorías
	@GetMapping
	public List<Categoria> getAllCategorias() {
		return categoriaService.findAll();
	}

	// Obtener una categoría por ID
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) {
		Optional<Categoria> categoria = categoriaService.findById(id);
		if (categoria.isPresent()) {
			return ResponseEntity.ok(categoria.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Crear una nueva categoría
	@PostMapping
	public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria) {
		Categoria nuevaCategoria = categoriaService.save(categoria);
		return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
	}

	// Actualizar una categoría existente
	@PatchMapping("/{id}")
	public ResponseEntity<Categoria> updateCategoria(@PathVariable Long id, @RequestBody Categoria categoriaDetalles) {
		Optional<Categoria> categoriaExistente = categoriaService.findById(id);
		if (categoriaExistente.isPresent()) {
			Categoria categoria = categoriaExistente.get();
			if (categoriaDetalles.getNombre() != null) {
				categoria.setNombre(categoriaDetalles.getNombre());
			}
			if (categoriaDetalles.getDescripcion() != null) {
				categoria.setDescripcion(categoriaDetalles.getDescripcion());
			}
			if (categoriaDetalles.getSlug() != null) {
				categoria.setSlug(categoriaDetalles.getSlug());
			}
			Categoria categoriaActualizada = categoriaService.save(categoria);
			return ResponseEntity.ok(categoriaActualizada);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Eliminar una categoría por ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
		Optional<Categoria> categoria = categoriaService.findById(id);
		if (categoria.isPresent()) {
			categoriaService.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
