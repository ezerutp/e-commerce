package pe.desarrolloweb.backend.modules.carrito.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import pe.desarrolloweb.backend.shared.enums.EstadoCarrito;

public record CarritoResponse(
    Long id,
    Long usuarioId,
    EstadoCarrito estado,
    BigDecimal subtotal,
    BigDecimal descuentoTotal,
    BigDecimal impuestos,
    BigDecimal total,
    String moneda,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    List<CarritoItemResponse> items
) {}
