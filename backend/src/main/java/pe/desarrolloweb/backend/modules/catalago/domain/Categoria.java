package pe.desarrolloweb.backend.modules.catalago.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorias",
       uniqueConstraints = @UniqueConstraint(name = "uk_categorias_nombre", columnNames = "nombre"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String nombre;

    @Size(max = 255)
    private String descripcion;

    @Column(length = 140, unique = true)
    private String slug;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (slug == null || slug.isBlank()) slug = generateSlug(nombre);
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (slug == null || slug.isBlank()) slug = generateSlug(nombre);
    }

    private String generateSlug(String nombre) {
        if (nombre == null) return null;
        return nombre.toLowerCase()
                .replaceAll("[^a-z0-9\s-]", "")
                .replaceAll("\s+", "-")
                .replaceAll("-+", "-")
                .replaceAll("^-|-$", "");
    }
}