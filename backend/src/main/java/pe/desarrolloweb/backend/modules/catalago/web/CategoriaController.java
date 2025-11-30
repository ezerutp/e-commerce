package pe.desarrolloweb.backend.modules.catalago.web;

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

import jakarta.validation.Valid;
import pe.desarrolloweb.backend.modules.catalago.domain.Categoria;
import pe.desarrolloweb.backend.modules.catalago.mapper.CategoriaMapper;
import pe.desarrolloweb.backend.modules.catalago.service.CategoriaService;
import pe.desarrolloweb.backend.modules.catalago.web.dto.CategoriaRequest;
import pe.desarrolloweb.backend.modules.catalago.web.dto.CategoriaResponse;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    
	@Autowired
	private CategoriaService categoriaService;

	// Obtener todas las categorías
	@GetMapping
	public List<CategoriaResponse> getAllCategorias() {
		return categoriaService.findAll().stream()
				.map(CategoriaMapper::toResponse)
				.toList();
	}

	// Obtener una categoría por ID
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaResponse> getCategoriaById(@PathVariable("id") Long id) {
		Optional<Categoria> categoria = categoriaService.findById(id);
		if (categoria.isPresent()) {
			CategoriaResponse response = CategoriaMapper.toResponse(categoria.get());
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Crear una nueva categoría
	@PostMapping
	public ResponseEntity<CategoriaResponse> createCategoria(@Valid @RequestBody CategoriaRequest request) {
		Categoria categoria = CategoriaMapper.toEntity(request);
		Categoria nuevaCategoria = categoriaService.save(categoria);
		CategoriaResponse response = CategoriaMapper.toResponse(nuevaCategoria);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Actualizar una categoría existente
	@PatchMapping("/{id}")
	public ResponseEntity<CategoriaResponse> updateCategoria(@PathVariable("id") Long id, @Valid @RequestBody CategoriaRequest request) {
		Optional<Categoria> categoriaExistente = categoriaService.findById(id);
		if (categoriaExistente.isPresent()) {
			Categoria categoria = categoriaExistente.get();
			if (request.nombre() != null) {
				categoria.setNombre(request.nombre());
			}
			if (request.descripcion() != null) {
				categoria.setDescripcion(request.descripcion());
			}
			if (request.slug() != null) {
				categoria.setSlug(request.slug());
			}
			Categoria categoriaActualizada = categoriaService.save(categoria);
			CategoriaResponse response = CategoriaMapper.toResponse(categoriaActualizada);
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Eliminar una categoría por ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategoria(@PathVariable("id") Long id) {
		Optional<Categoria> categoria = categoriaService.findById(id);
		if (categoria.isPresent()) {
			categoriaService.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
