package pe.desarrolloweb.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.desarrolloweb.backend.entities.PedidoItem;
import pe.desarrolloweb.backend.services.PedidoItemService;

@RestController
@RequestMapping("/api/pedido-items")
public class PedidoItemController {

    @Autowired
    private PedidoItemService pedidoItemService;

    @GetMapping
    public List<PedidoItem> getAllPedidoItems() {
        return pedidoItemService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoItem> getPedidoItemById(@PathVariable Long id) {
        Optional<PedidoItem> item = pedidoItemService.findById(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PedidoItem> createPedidoItem(@RequestBody PedidoItem pedidoItem) {
        PedidoItem nuevo = pedidoItemService.save(pedidoItem);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PedidoItem> updatePedidoItem(@PathVariable Long id, @RequestBody PedidoItem detalles) {
        Optional<PedidoItem> existente = pedidoItemService.findById(id);
        if (existente.isPresent()) {
            PedidoItem item = existente.get();
            if (detalles.getPedidoId() != null) item.setPedidoId(detalles.getPedidoId());
            if (detalles.getVarianteId() != null) item.setVarianteId(detalles.getVarianteId());
            if (detalles.getSkuSnapshot() != null) item.setSkuSnapshot(detalles.getSkuSnapshot());
            if (detalles.getNombreProductoSnapshot() != null) item.setNombreProductoSnapshot(detalles.getNombreProductoSnapshot());
            if (detalles.getPrecioUnitario() != null) item.setPrecioUnitario(detalles.getPrecioUnitario());
            if (detalles.getCantidad() != null) item.setCantidad(detalles.getCantidad());

            PedidoItem actualizado = pedidoItemService.save(item);
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedidoItem(@PathVariable Long id) {
        if (pedidoItemService.findById(id).isPresent()) {
            pedidoItemService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}