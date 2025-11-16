package pe.desarrolloweb.backend.modules.carrito.web.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CarritoItemRequest(
    
    @NotNull(message = "El ID del carrito es obligatorio")
    @Positive(message = "El ID del carrito debe ser positivo")
    Long carritoId,
    
    @NotNull(message = "El ID de la variante es obligatorio")
    @Positive(message = "El ID de la variante debe ser positivo")
    Long varianteId,
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    Integer cantidad,
    
    @NotNull(message = "El precio unitario es obligatorio")
    @Digits(integer = 12, fraction = 2, message = "El precio debe tener un máximo de 12 enteros y 2 decimales")
    BigDecimal precioUnitario
    
) {
}
