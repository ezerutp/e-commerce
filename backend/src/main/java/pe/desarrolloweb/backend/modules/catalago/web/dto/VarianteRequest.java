package pe.desarrolloweb.backend.modules.catalago.web.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record VarianteRequest(
    
    @NotNull(message = "El ID del producto es obligatorio")
    @Positive(message = "El ID del producto debe ser positivo")
    Long productoId,
    
    @NotBlank(message = "El SKU es obligatorio")
    @Size(max = 100, message = "El SKU no puede exceder 100 caracteres")
    String sku,
    
    @Digits(integer = 12, fraction = 2, message = "El precio debe tener máximo 12 dígitos enteros y 2 decimales")
    BigDecimal precio,
    
    @Digits(integer = 12, fraction = 2, message = "El peso debe tener máximo 12 dígitos enteros y 2 decimales")
    BigDecimal peso,
    
    String atributosJson,
    
    @NotNull(message = "El estado activo es obligatorio")
    Boolean activo
) {
}
