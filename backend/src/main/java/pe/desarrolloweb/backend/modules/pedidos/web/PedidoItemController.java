package pe.desarrolloweb.backend.modules.pedidos.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import pe.desarrolloweb.backend.modules.pedidos.domain.PedidoItem;
import pe.desarrolloweb.backend.modules.pedidos.mapper.PedidoItemMapper;
import pe.desarrolloweb.backend.modules.pedidos.service.PedidoItemService;
import pe.desarrolloweb.backend.modules.pedidos.web.dto.PedidoItemRequest;
import pe.desarrolloweb.backend.modules.pedidos.web.dto.PedidoItemResponse;

@RestController
@RequestMapping("/api/pedido-items")
public class PedidoItemController {

    @Autowired
    private PedidoItemService pedidoItemService;

    @GetMapping
    public List<PedidoItemResponse> getAllPedidoItems() {
        return pedidoItemService.findAll().stream()
                .map(PedidoItemMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoItemResponse> getPedidoItemById(@PathVariable("id") Long id) {
        Optional<PedidoItem> item = pedidoItemService.findById(id);
        if (item.isPresent()) {
            PedidoItemResponse response = PedidoItemMapper.toResponse(item.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PedidoItemResponse> createPedidoItem(@Valid @RequestBody PedidoItemRequest request) {
        PedidoItem pedidoItem = PedidoItemMapper.toEntity(request);
        PedidoItem nuevo = pedidoItemService.save(pedidoItem);
        PedidoItemResponse response = PedidoItemMapper.toResponse(nuevo);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PedidoItemResponse> updatePedidoItem(@PathVariable("id") Long id, @Valid @RequestBody PedidoItemRequest request) {
        Optional<PedidoItem> existente = pedidoItemService.findById(id);
        if (existente.isPresent()) {
            PedidoItem item = existente.get();
            if (request.pedidoId() != null) {
                item.getPedido().setId(request.pedidoId());
            }
            if (request.varianteId() != null) {
                item.getVariante().setId(request.varianteId());
            }
            if (request.skuSnapshot() != null) {
                item.setSkuSnapshot(request.skuSnapshot());
            }
            if (request.nombreProductoSnapshot() != null) {
                item.setNombreProductoSnapshot(request.nombreProductoSnapshot());
            }
            if (request.precioUnitario() != null) {
                item.setPrecioUnitario(request.precioUnitario());
            }
            if (request.cantidad() != null) {
                item.setCantidad(request.cantidad());
            }

            PedidoItem actualizado = pedidoItemService.save(item);
            PedidoItemResponse response = PedidoItemMapper.toResponse(actualizado);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedidoItem(@PathVariable("id") Long id) {
        if (pedidoItemService.findById(id).isPresent()) {
            pedidoItemService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}