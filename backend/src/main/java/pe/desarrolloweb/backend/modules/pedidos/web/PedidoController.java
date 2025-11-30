package pe.desarrolloweb.backend.modules.pedidos.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import pe.desarrolloweb.backend.modules.pedidos.domain.Pedido;
import pe.desarrolloweb.backend.modules.pedidos.mapper.PedidoMapper;
import pe.desarrolloweb.backend.modules.pedidos.service.PedidoService;
import pe.desarrolloweb.backend.modules.pedidos.web.dto.PedidoRequest;
import pe.desarrolloweb.backend.modules.pedidos.web.dto.PedidoResponse;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Obtener todos los pedidos
    @GetMapping
    public List<PedidoResponse> getAllPedidos() {
        return pedidoService.findAll().stream()
                .map(PedidoMapper::toResponse)
                .toList();
    }

    // Obtener un pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> getPedidoById(@PathVariable("id") Long id) {
        Optional<Pedido> pedido = pedidoService.findById(id);
        if (pedido.isPresent()) {
            PedidoResponse response = PedidoMapper.toResponse(pedido.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    // Crear un nuevo pedido
    @PostMapping
    public ResponseEntity<PedidoResponse> createPedido(@Valid @RequestBody PedidoRequest request) {
        Pedido pedido = PedidoMapper.toEntity(request);
        Pedido nuevoPedido = pedidoService.save(pedido);
        PedidoResponse response = PedidoMapper.toResponse(nuevoPedido);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Actualizar un pedido existente
    @PatchMapping("/{id}")
    public ResponseEntity<PedidoResponse> updatePedido(@PathVariable("id") Long id, @Valid @RequestBody PedidoRequest request) {
        Optional<Pedido> existente = pedidoService.findById(id);
        if (existente.isPresent()) {
            Pedido pedido = existente.get();
            if (request.usuarioId() != null) {
                pedido.getUsuario().setId(request.usuarioId());
            }
            if (request.numeroOrden() != null) {
                pedido.setNumeroOrden(request.numeroOrden());
            }
            if (request.moneda() != null) {
                pedido.setMoneda(request.moneda());
            }
            if (request.estado() != null) {
                pedido.setEstado(request.estado());
            }
            if (request.subtotal() != null) {
                pedido.setSubtotal(request.subtotal());
            }
            if (request.impuestos() != null) {
                pedido.setImpuestos(request.impuestos());
            }
            if (request.total() != null) {
                pedido.setTotal(request.total());
            }

            Pedido actualizado = pedidoService.save(pedido);
            PedidoResponse response = PedidoMapper.toResponse(actualizado);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar un pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable("id") Long id) {
        Optional<Pedido> pedido = pedidoService.findById(id);
        if (pedido.isPresent()) {
            pedidoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
