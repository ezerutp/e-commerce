package pe.desarrolloweb.backend.modules.catalago.web.dto;

import java.time.LocalDateTime;

public record CategoriaResponse(
    Long id,
    String nombre,
    String descripcion,
    String slug,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
