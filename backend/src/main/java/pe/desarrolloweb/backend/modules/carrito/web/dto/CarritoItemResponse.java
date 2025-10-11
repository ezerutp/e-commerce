package pe.desarrolloweb.backend.modules.carrito.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CarritoItemResponse(
    Long id,
    Long carritoId,
    Long varianteId,
    Integer cantidad,
    BigDecimal precioUnitario,
    LocalDateTime updatedAt
) {}