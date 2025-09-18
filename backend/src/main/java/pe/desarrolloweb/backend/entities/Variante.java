package pe.desarrolloweb.backend.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "variantes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Variante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false, foreignKey = @ForeignKey(name = "fk_variante_producto"))
    private Producto producto;

    @NotBlank(message = "El SKU es obligatorio")
    @Size(max = 100, message = "El SKU no puede exceder 100 caracteres")
    @Column(nullable = false, length = 100)
    private String sku;

    @Digits(integer = 12, fraction = 2)
    @Column(name = "precio", precision = 12, scale = 2)
    private BigDecimal precio = BigDecimal.ZERO;

    @Digits(integer = 12, fraction = 2)
    @Column(name = "peso", precision = 12, scale = 2)
    private BigDecimal peso = BigDecimal.ZERO;

    @Column(name = "atributos_json", columnDefinition = "TEXT")
    private String atributosJson;

    @NotNull(message = "El estado activo es obligatorio")
    private boolean activo;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
