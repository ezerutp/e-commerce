package pe.desarrolloweb.backend.modules.pagos.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import pe.desarrolloweb.backend.shared.enums.EstadoPago;
import pe.desarrolloweb.backend.shared.enums.MetodoPago;

public record PagoResponse(
    Long id,
    Long pedidoId,
    MetodoPago metodo,
    String proveedor,
    BigDecimal monto,
    String moneda,
    EstadoPago estado,
    String referenciaProveedor,
    LocalDateTime autorizadoEn,
    LocalDateTime capturadoEn,
    LocalDateTime updatedAt
) {
}
