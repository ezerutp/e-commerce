package pe.desarrolloweb.backend.modules.pedidos.web.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PedidoItemRequest(
    
    @NotNull(message = "El ID del pedido es obligatorio")
    @Positive(message = "El ID del pedido debe ser positivo")
    Long pedidoId,
    
    @NotNull(message = "El ID de la variante es obligatorio")
    @Positive(message = "El ID de la variante debe ser positivo")
    Long varianteId,
    
    @Size(max = 120, message = "El SKU no puede exceder 120 caracteres")
    String skuSnapshot,
    
    @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
    String nombreProductoSnapshot,
    
    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.00", message = "El precio unitario no puede ser negativo")
    @Digits(integer = 12, fraction = 2, message = "El precio debe tener máximo 12 dígitos enteros y 2 decimales")
    BigDecimal precioUnitario,
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    Integer cantidad
) {
}
