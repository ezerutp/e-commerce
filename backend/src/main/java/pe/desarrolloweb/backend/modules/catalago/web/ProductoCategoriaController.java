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
import pe.desarrolloweb.backend.modules.catalago.domain.ProductoCategoria;
import pe.desarrolloweb.backend.modules.catalago.mapper.ProductoCategoriaMapper;
import pe.desarrolloweb.backend.modules.catalago.service.ProductoCategoriaService;
import pe.desarrolloweb.backend.modules.catalago.web.dto.ProductoCategoriaRequest;
import pe.desarrolloweb.backend.modules.catalago.web.dto.ProductoCategoriaResponse;

@RestController
@RequestMapping("/api/producto-categorias")
public class ProductoCategoriaController {
    
	@Autowired
	private ProductoCategoriaService productoCategoriaService;

	// Obtener todas las relaciones producto-categoría
	@GetMapping
	public List<ProductoCategoriaResponse> getAllProductoCategorias() {
		return productoCategoriaService.findAll().stream()
				.map(ProductoCategoriaMapper::toResponse)
				.toList();
	}

	// Obtener una relación producto-categoría por ID
	@GetMapping("/{id}")
	public ResponseEntity<ProductoCategoriaResponse> getProductoCategoriaById(@PathVariable("id") Long id) {
		Optional<ProductoCategoria> productoCategoria = productoCategoriaService.findById(id);
		if (productoCategoria.isPresent()) {
			ProductoCategoriaResponse response = ProductoCategoriaMapper.toResponse(productoCategoria.get());
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Crear una nueva relación producto-categoría
	@PostMapping
	public ResponseEntity<ProductoCategoriaResponse> createProductoCategoria(@Valid @RequestBody ProductoCategoriaRequest request) {
		ProductoCategoria productoCategoria = ProductoCategoriaMapper.toEntity(request);
		ProductoCategoria nuevaProductoCategoria = productoCategoriaService.save(productoCategoria);
		ProductoCategoriaResponse response = ProductoCategoriaMapper.toResponse(nuevaProductoCategoria);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Actualizar una relación producto-categoría existente
	@PatchMapping("/{id}")
	public ResponseEntity<ProductoCategoriaResponse> updateProductoCategoria(@PathVariable("id") Long id, @Valid @RequestBody ProductoCategoriaRequest request) {
		Optional<ProductoCategoria> existente = productoCategoriaService.findById(id);
		if (existente.isPresent()) {
			ProductoCategoria pc = existente.get();
			// Actualizar las relaciones si se proporcionan
			if (request.productoId() != null) {
				pc.getProducto().setId(request.productoId());
			}
			if (request.categoriaId() != null) {
				pc.getCategoria().setId(request.categoriaId());
			}
			ProductoCategoria actualizado = productoCategoriaService.save(pc);
			ProductoCategoriaResponse response = ProductoCategoriaMapper.toResponse(actualizado);
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Eliminar una relación producto-categoría por ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProductoCategoria(@PathVariable("id") Long id) {
		Optional<ProductoCategoria> productoCategoria = productoCategoriaService.findById(id);
		if (productoCategoria.isPresent()) {
			productoCategoriaService.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
