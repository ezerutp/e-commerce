package pe.desarrolloweb.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.desarrolloweb.backend.entities.CarritoItem;
import pe.desarrolloweb.backend.services.CarritoItemService;

@RestController
@RequestMapping("/api/carrito-items")
public class CarritoItemController {

    @Autowired
    private CarritoItemService carritoItemService;

    // Obtener todos los items
    @GetMapping
    public List<CarritoItem> getAllCarritoItems() {
        return carritoItemService.findAll();
    }

    // Obtener un item por ID
    @GetMapping("/{id}")
    public ResponseEntity<CarritoItem> getCarritoItemById(@PathVariable Long id) {
        Optional<CarritoItem> carritoItem = carritoItemService.findById(id);
        return carritoItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo item
    @PostMapping
    public ResponseEntity<CarritoItem> createCarritoItem(@RequestBody CarritoItem carritoItem) {
        CarritoItem nuevoItem = carritoItemService.save(carritoItem);
        return new ResponseEntity<>(nuevoItem, HttpStatus.CREATED);
    }

    // Actualizar un item existente
    @PatchMapping("/{id}")
    public ResponseEntity<CarritoItem> updateCarritoItem(@PathVariable Long id, @RequestBody CarritoItem carritoItemDetalles) {
        Optional<CarritoItem> existente = carritoItemService.findById(id);
        if (existente.isPresent()) {
            CarritoItem item = existente.get();
            if (carritoItemDetalles.getCarritoId() != null) item.setCarritoId(carritoItemDetalles.getCarritoId());
            if (carritoItemDetalles.getVarianteId() != null) item.setVarianteId(carritoItemDetalles.getVarianteId());
            if (carritoItemDetalles.getCantidad() != null) item.setCantidad(carritoItemDetalles.getCantidad());
            if (carritoItemDetalles.getPrecioUnitario() != null) item.setPrecioUnitario(carritoItemDetalles.getPrecioUnitario());

            return ResponseEntity.ok(carritoItemService.save(item));
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar un item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarritoItem(@PathVariable Long id) {
        Optional<CarritoItem> item = carritoItemService.findById(id);
        if (item.isPresent()) {
            carritoItemService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
