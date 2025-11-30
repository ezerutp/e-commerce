package pe.desarrolloweb.backend.modules.pagos.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import pe.desarrolloweb.backend.shared.enums.EstadoPago;
import pe.desarrolloweb.backend.shared.enums.MetodoPago;

public record PagoRequest(
    
    @NotNull(message = "El ID del pedido es obligatorio")
    @Positive(message = "El ID del pedido debe ser positivo")
    Long pedidoId,
    
    @NotNull(message = "El método de pago es obligatorio")
    MetodoPago metodo,
    
    @Size(max = 120, message = "El proveedor no puede exceder 120 caracteres")
    String proveedor,
    
    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.00", message = "El monto no puede ser negativo")
    @Digits(integer = 12, fraction = 2, message = "El monto debe tener máximo 12 dígitos enteros y 2 decimales")
    BigDecimal monto,
    
    @NotNull(message = "La moneda es obligatoria")
    @Size(max = 10, message = "La moneda no puede exceder 10 caracteres")
    String moneda,
    
    @NotNull(message = "El estado del pago es obligatorio")
    EstadoPago estado,
    
    @Size(max = 255, message = "La referencia no puede exceder 255 caracteres")
    String referenciaProveedor,
    
    LocalDateTime autorizadoEn,
    
    LocalDateTime capturadoEn
) {
}
