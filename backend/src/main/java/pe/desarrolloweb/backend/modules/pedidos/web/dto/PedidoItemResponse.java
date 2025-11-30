package pe.desarrolloweb.backend.modules.pedidos.web.dto;

import java.math.BigDecimal;

public record PedidoItemResponse(
    Long id,
    Long pedidoId,
    Long varianteId,
    String skuSnapshot,
    String nombreProductoSnapshot,
    BigDecimal precioUnitario,
    Integer cantidad
) {
}
