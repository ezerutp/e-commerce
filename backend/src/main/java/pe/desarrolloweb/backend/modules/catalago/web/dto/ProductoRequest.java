package pe.desarrolloweb.backend.modules.catalago.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductoRequest(
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
    String nombre,
    
    String descripcion,
    
    @Size(max = 120, message = "La marca no puede exceder 120 caracteres")
    String marca,
    
    Boolean activo,
    
    @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "El slug debe tener formato válido (solo letras minúsculas, números y guiones)")
    @Size(max = 255, message = "El slug no puede exceder 255 caracteres")
    String slug
) {
}
