package pe.desarrolloweb.backend.modules.inventario.domain;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.desarrolloweb.backend.modules.catalago.domain.Variante;

@Entity
@Table(name = "inventarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "La variante no puede ser nula")
    @JoinColumn(name = "variante_id", nullable = false, foreignKey = @ForeignKey(name = "fk_inventario_variante"))
    private Variante variante;

    @NotNull(message = "El stock disponible no puede ser nulo")
    @Min(value = 0, message = "El stock disponible no puede ser negativo")
    private Integer stockDisponible;

    @NotNull(message = "El stock reservado no puede ser nulo")
    @Min(value = 0, message = "El stock reservado no puede ser negativo")
    private Integer stockReservado;

    @Column(name = "updated_at")
    private LocalTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalTime.now();
    }
    
}
