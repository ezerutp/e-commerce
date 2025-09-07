package pe.desarrolloweb.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.desarrolloweb.backend.entities.Pedido;
import pe.desarrolloweb.backend.services.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Obtener todos los pedidos
    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.findAll();
    }

    // Obtener un pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.findById(id);
        return pedido.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo pedido
    @PostMapping
    public ResponseEntity<Pedido> createPedido(@RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoService.save(pedido);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
    }

    // Actualizar un pedido existente
    @PatchMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@PathVariable Long id, @RequestBody Pedido pedidoDetalles) {
        Optional<Pedido> existente = pedidoService.findById(id);
        if (existente.isPresent()) {
            Pedido pedido = existente.get();
            if (pedidoDetalles.getClienteId() != null) pedido.setClienteId(pedidoDetalles.getClienteId());
            if (pedidoDetalles.getNumeroOrden() != null) pedido.setNumeroOrden(pedidoDetalles.getNumeroOrden());
            if (pedidoDetalles.getMoneda() != null) pedido.setMoneda(pedidoDetalles.getMoneda());
            if (pedidoDetalles.getEstado() != null) pedido.setEstado(pedidoDetalles.getEstado());
            if (pedidoDetalles.getSubtotal() != null) pedido.setSubtotal(pedidoDetalles.getSubtotal());
            if (pedidoDetalles.getImpuestos() != null) pedido.setImpuestos(pedidoDetalles.getImpuestos());
            if (pedidoDetalles.getTotal() != null) pedido.setTotal(pedidoDetalles.getTotal());

            return ResponseEntity.ok(pedidoService.save(pedido));
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar un pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.findById(id);
        if (pedido.isPresent()) {
            pedidoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
