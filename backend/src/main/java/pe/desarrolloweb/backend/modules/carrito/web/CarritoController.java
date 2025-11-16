package pe.desarrolloweb.backend.modules.carrito.web;

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
import pe.desarrolloweb.backend.modules.carrito.domain.Carrito;
import pe.desarrolloweb.backend.modules.carrito.mapper.CarritoMapper;
import pe.desarrolloweb.backend.modules.carrito.service.CarritoService;
import pe.desarrolloweb.backend.modules.carrito.web.dto.CarritoRequest;
import pe.desarrolloweb.backend.modules.carrito.web.dto.CarritoResponse;

@RestController
@RequestMapping("/api/carritos")
public class CarritoController {
    
	@Autowired
	private CarritoService carritoService;

	// Obtener todos los carritos
	@GetMapping
	public List<CarritoResponse> getAllCarritos() {
		return carritoService.findAll().stream()
				.map(CarritoMapper::toResponse)
				.toList();
	}

	// Obtener un carrito por ID
	@GetMapping("/{id}")
	public ResponseEntity<CarritoResponse> getCarritoById(@PathVariable Long id) {
		Optional<Carrito> carrito = carritoService.findById(id);
		if (carrito.isPresent()) {
			CarritoResponse response = CarritoMapper.toResponse(carrito.get());
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Crear un nuevo carrito
	@PostMapping
	public ResponseEntity<CarritoResponse> createCarrito(@Valid @RequestBody CarritoRequest request) {
		Carrito carrito = CarritoMapper.toEntity(request);
		Carrito nuevoCarrito = carritoService.save(carrito);
		CarritoResponse response = CarritoMapper.toResponse(nuevoCarrito);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Actualizar un carrito existente
	@PatchMapping("/{id}")
	public ResponseEntity<CarritoResponse> updateCarrito(@PathVariable Long id, @Valid @RequestBody CarritoRequest request) {
		Optional<Carrito> carritoExistente = carritoService.findById(id);
		if (carritoExistente.isPresent()) {
			Carrito carrito = carritoExistente.get();
			
			// Actualizar campos del request al carrito existente
			if (request.usuarioId() != null) {
				carrito.getUsuario().setId(request.usuarioId());
			}
			if (request.estado() != null) {
				carrito.setEstado(request.estado());
			}
			if (request.subtotal() != null) {
				carrito.setSubtotal(request.subtotal());
			}
			if (request.descuentoTotal() != null) {
				carrito.setDescuentoTotal(request.descuentoTotal());
			}
			if (request.impuestos() != null) {
				carrito.setImpuestos(request.impuestos());
			}
			if (request.total() != null) {
				carrito.setTotal(request.total());
			}
			if (request.moneda() != null) {
				carrito.setMoneda(request.moneda());
			}
			
			Carrito carritoActualizado = carritoService.save(carrito);
			CarritoResponse response = CarritoMapper.toResponse(carritoActualizado);
			return ResponseEntity.ok(response);
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
