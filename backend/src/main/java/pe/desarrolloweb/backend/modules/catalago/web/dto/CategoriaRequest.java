package pe.desarrolloweb.backend.modules.catalago.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequest(
    
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 120, message = "El nombre no puede exceder 120 caracteres")
    String nombre,
    
    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres")
    String descripcion,
    
    @Size(max = 140, message = "El slug no puede exceder 140 caracteres")
    String slug
) {
}
