package pe.desarrolloweb.backend.modules.catalago.web.dto;

import java.time.LocalDateTime;

public record ProductoResponse(
    Long id,
    String nombre,
    String descripcion,
    String marca,
    Boolean activo,
    String slug,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
