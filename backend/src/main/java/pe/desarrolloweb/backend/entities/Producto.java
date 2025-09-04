package pe.desarrolloweb.backend.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Size(max = 120, message = "La marca no puede exceder 120 caracteres")
    private String marca;

    @Column(nullable = false)
    private Boolean activo = true;

    @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "El slug debe tener formato válido (solo letras minúsculas, números y guiones)")
    @Size(max = 255, message = "El slug no puede exceder 255 caracteres")
    @Column(unique = true)
    private String slug;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (activo == null) {
            activo = true;
        }
        if (slug == null || slug.isEmpty()) {
            slug = generateSlug(nombre);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    private String generateSlug(String nombre) {
        if (nombre == null) return null;
        return nombre.toLowerCase()
                     .replaceAll("[^a-z0-9\\s-]", "")
                     .replaceAll("\\s+", "-")
                     .replaceAll("-+", "-")
                     .replaceAll("^-|-$", "");
    }
}
