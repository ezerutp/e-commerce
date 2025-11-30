package pe.desarrolloweb.backend.modules.catalago.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VarianteResponse(
    Long id,
    Long productoId,
    String productoNombre,
    String sku,
    BigDecimal precio,
    BigDecimal peso,
    String atributosJson,
    Boolean activo,
    LocalDateTime updatedAt
) {
}
