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

import pe.desarrolloweb.backend.modules.catalago.domain.ProductoCategoria;
import pe.desarrolloweb.backend.modules.catalago.service.ProductoCategoriaService;

@RestController
@RequestMapping("/api/producto-categorias")
public class ProductoCategoriaController {
    
	@Autowired
	private ProductoCategoriaService productoCategoriaService;

	// Obtener todas las relaciones producto-categoría
	@GetMapping
	public List<ProductoCategoria> getAllProductoCategorias() {
		return productoCategoriaService.findAll();
	}

	// Obtener una relación producto-categoría por ID
	@GetMapping("/{id}")
	public ResponseEntity<ProductoCategoria> getProductoCategoriaById(@PathVariable Long id) {
		Optional<ProductoCategoria> productoCategoria = productoCategoriaService.findById(id);
		if (productoCategoria.isPresent()) {
			return ResponseEntity.ok(productoCategoria.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Crear una nueva relación producto-categoría
	@PostMapping
	public ResponseEntity<ProductoCategoria> createProductoCategoria(@RequestBody ProductoCategoria productoCategoria) {
		ProductoCategoria nuevaProductoCategoria = productoCategoriaService.save(productoCategoria);
		return new ResponseEntity<>(nuevaProductoCategoria, HttpStatus.CREATED);
	}

	// Actualizar una relación producto-categoría existente
	@PatchMapping("/{id}")
	public ResponseEntity<ProductoCategoria> updateProductoCategoria(@PathVariable Long id, @RequestBody ProductoCategoria detalles) {
		Optional<ProductoCategoria> existente = productoCategoriaService.findById(id);
		if (existente.isPresent()) {
			ProductoCategoria pc = existente.get();
			// No se actualizan producto ni categoria por integridad relacional
			ProductoCategoria actualizado = productoCategoriaService.save(pc);
			return ResponseEntity.ok(actualizado);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Eliminar una relación producto-categoría por ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProductoCategoria(@PathVariable Long id) {
		Optional<ProductoCategoria> productoCategoria = productoCategoriaService.findById(id);
		if (productoCategoria.isPresent()) {
			productoCategoriaService.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
