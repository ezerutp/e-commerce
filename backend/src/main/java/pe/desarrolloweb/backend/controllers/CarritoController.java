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

import pe.desarrolloweb.backend.entities.Carrito;
import pe.desarrolloweb.backend.services.CarritoService;

@RestController
@RequestMapping("/api/carritos")
public class CarritoController {
    
	@Autowired
	private CarritoService carritoService;

	// Obtener todos los carritos
	@GetMapping
	public List<Carrito> getAllCarritos() {
		return carritoService.findAll();
	}

	// Obtener un carrito por ID
	@GetMapping("/{id}")
	public ResponseEntity<Carrito> getCarritoById(@PathVariable Long id) {
		Optional<Carrito> carrito = carritoService.findById(id);
		if (carrito.isPresent()) {
			return ResponseEntity.ok(carrito.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Crear un nuevo carrito
	@PostMapping
	public ResponseEntity<Carrito> createCarrito(@RequestBody Carrito carrito) {
		Carrito nuevoCarrito = carritoService.save(carrito);
		return new ResponseEntity<>(nuevoCarrito, HttpStatus.CREATED);
	}

	// Actualizar un carrito existente
	@PatchMapping("/{id}")
	public ResponseEntity<Carrito> updateCarrito(@PathVariable Long id, @RequestBody Carrito carritoDetalles) {
		Optional<Carrito> carritoExistente = carritoService.findById(id);
		if (carritoExistente.isPresent()) {
			Carrito carrito = carritoExistente.get();
			// Actualizar campos según los setters disponibles
			if (carritoDetalles.getUsuario() != null) {
				carrito.setUsuario(carritoDetalles.getUsuario());
			}
			if (carritoDetalles.getEstado() != null) {
				carrito.setEstado(carritoDetalles.getEstado());
			}
			Carrito carritoActualizado = carritoService.save(carrito);
			return ResponseEntity.ok(carritoActualizado);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Eliminar un carrito por ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCarrito(@PathVariable Long id) {
		Optional<Carrito> carrito = carritoService.findById(id);
		if (carrito.isPresent()) {
			carritoService.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
