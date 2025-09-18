package pe.desarrolloweb.backend.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carrito_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrito_id", nullable = false, foreignKey = @ForeignKey(name = "fk_carritoitem_carrito"))
    private Carrito carrito;

    @NotNull(message = "El variante es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "variante_id", nullable = false, foreignKey = @ForeignKey(name = "fk_carritoitem_variante"))
    private Variante variante;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @Digits(integer = 12, fraction = 2, message = "El precio debe tener un máximo de 12 enteros y 2 decimales")
    @Column(name = "precio_unitario", precision = 12, scale = 2, nullable = false)
    private BigDecimal precioUnitario;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
