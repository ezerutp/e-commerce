package pe.desarrolloweb.backend.modules.pedidos.web.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import pe.desarrolloweb.backend.shared.enums.EstadoPedido;

public record PedidoRequest(
    
    @NotNull(message = "El ID del usuario es obligatorio")
    @Positive(message = "El ID del usuario debe ser positivo")
    Long usuarioId,
    
    @NotBlank(message = "El número de orden es obligatorio")
    @Size(max = 80, message = "El número de orden no puede exceder 80 caracteres")
    String numeroOrden,
    
    @NotBlank(message = "La moneda es obligatoria")
    @Size(max = 10, message = "La moneda no puede exceder 10 caracteres")
    String moneda,
    
    @NotNull(message = "El estado es obligatorio")
    EstadoPedido estado,
    
    @NotNull(message = "El subtotal es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El subtotal debe ser mayor o igual a 0")
    @Digits(integer = 12, fraction = 2, message = "El subtotal debe tener máximo 12 dígitos enteros y 2 decimales")
    BigDecimal subtotal,
    
    @NotNull(message = "Los impuestos son obligatorios")
    @DecimalMin(value = "0.0", inclusive = true, message = "Los impuestos deben ser mayor o igual a 0")
    @Digits(integer = 12, fraction = 2, message = "Los impuestos deben tener máximo 12 dígitos enteros y 2 decimales")
    BigDecimal impuestos,
    
    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El total debe ser mayor o igual a 0")
    @Digits(integer = 12, fraction = 2, message = "El total debe tener máximo 12 dígitos enteros y 2 decimales")
    BigDecimal total
) {
}
