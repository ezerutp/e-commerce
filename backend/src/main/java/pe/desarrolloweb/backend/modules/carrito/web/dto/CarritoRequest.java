package pe.desarrolloweb.backend.modules.carrito.web.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import pe.desarrolloweb.backend.shared.enums.EstadoCarrito;

public record CarritoRequest(
    
    @NotNull(message = "El ID del usuario es obligatorio")
    @Positive(message = "El ID del usuario debe ser positivo")
    Long usuarioId,
    
    @NotNull(message = "El estado del carrito es obligatorio")
    EstadoCarrito estado,
    
    @NotNull(message = "El subtotal es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El subtotal debe ser mayor o igual a 0")
    @Digits(integer = 12, fraction = 2, message = "El subtotal debe tener máximo 12 dígitos enteros y 2 decimales")
    BigDecimal subtotal,
    
    @NotNull(message = "El descuento total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El descuento debe ser mayor o igual a 0")
    @Digits(integer = 12, fraction = 2, message = "El descuento debe tener máximo 12 dígitos enteros y 2 decimales")
    BigDecimal descuentoTotal,
    
    @NotNull(message = "Los impuestos son obligatorios")
    @DecimalMin(value = "0.0", inclusive = true, message = "Los impuestos deben ser mayor o igual a 0")
    @Digits(integer = 12, fraction = 2, message = "Los impuestos deben tener máximo 12 dígitos enteros y 2 decimales")
    BigDecimal impuestos,
    
    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El total debe ser mayor o igual a 0")
    @Digits(integer = 12, fraction = 2, message = "El total debe tener máximo 12 dígitos enteros y 2 decimales")
    BigDecimal total,
    
    @NotBlank(message = "La moneda es obligatoria")
    @Size(max = 10, message = "La moneda debe tener máximo 10 caracteres")
    String moneda
) {
}
