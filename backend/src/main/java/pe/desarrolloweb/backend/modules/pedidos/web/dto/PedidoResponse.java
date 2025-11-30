package pe.desarrolloweb.backend.modules.pedidos.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import pe.desarrolloweb.backend.shared.enums.EstadoPedido;

public record PedidoResponse(
    Long id,
    Long usuarioId,
    String numeroOrden,
    String moneda,
    EstadoPedido estado,
    BigDecimal subtotal,
    BigDecimal impuestos,
    BigDecimal total,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
