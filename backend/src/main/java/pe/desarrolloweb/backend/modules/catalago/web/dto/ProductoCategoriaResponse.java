package pe.desarrolloweb.backend.modules.catalago.web.dto;

import java.time.LocalDateTime;

public record ProductoCategoriaResponse(
    Long id,
    Long productoId,
    String productoNombre,
    Long categoriaId,
    String categoriaNombre,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
