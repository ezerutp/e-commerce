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

import pe.desarrolloweb.backend.modules.carrito.domain.Carrito;
import pe.desarrolloweb.backend.modules.carrito.domain.CarritoItem;
import pe.desarrolloweb.backend.modules.carrito.mapper.CarritoItemMapper;
import pe.desarrolloweb.backend.modules.carrito.service.CarritoItemService;
import pe.desarrolloweb.backend.modules.carrito.web.dto.CarritoItemRequest;
import pe.desarrolloweb.backend.modules.carrito.web.dto.CarritoItemResponse;
import pe.desarrolloweb.backend.modules.catalago.domain.Variante;

@RestController
@RequestMapping("/api/carrito-items")
public class CarritoItemController {

    @Autowired
    private CarritoItemService carritoItemService;

    // Obtener todos los items
    @GetMapping
    public List<CarritoItemResponse> getAllCarritoItems() {
        return carritoItemService.findAll().stream()
                .map(CarritoItemMapper::toResponse)
                .toList();
    }

    // Obtener un item por ID
    @GetMapping("/{id}")
    public ResponseEntity<CarritoItemResponse> getCarritoItemById(@PathVariable("id") Long id) {
        Optional<CarritoItem> carritoItem = carritoItemService.findById(id);
        return carritoItem.map(CarritoItemMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo item
    @PostMapping
    public ResponseEntity<CarritoItemResponse> createCarritoItem(@RequestBody CarritoItemRequest carritoItem) {
        CarritoItem nuevoItem = carritoItemService.save(CarritoItemMapper.toEntity(carritoItem));
        return new ResponseEntity<>(CarritoItemMapper.toResponse(nuevoItem), HttpStatus.CREATED);
    }

    // Actualizar un item existente
    @PatchMapping("/{id}")
    public ResponseEntity<CarritoItemResponse> updateCarritoItem(@PathVariable("id") Long id, @RequestBody CarritoItemRequest carritoItemDetalles) {
        Optional<CarritoItem> existente = carritoItemService.findById(id);
        if (existente.isPresent()) {
            CarritoItem item = existente.get();

            // Actualizar campos del request al item existente
            Carrito carrito = new Carrito();
            carrito.setId(carritoItemDetalles.carritoId());
            if (carritoItemDetalles.carritoId() != null) item.setCarrito(carrito);

            Variante variante = new Variante();
            variante.setId(carritoItemDetalles.varianteId());
            if (carritoItemDetalles.varianteId() != null) item.setVariante(variante);
            
            if (carritoItemDetalles.cantidad() != null) item.setCantidad(carritoItemDetalles.cantidad());
            if (carritoItemDetalles.precioUnitario() != null) item.setPrecioUnitario(carritoItemDetalles.precioUnitario());

            return ResponseEntity.ok(CarritoItemMapper.toResponse(carritoItemService.save(item)));
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar un item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarritoItem(@PathVariable("id") Long id) {
        Optional<CarritoItem> item = carritoItemService.findById(id);
        if (item.isPresent()) {
            carritoItemService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
