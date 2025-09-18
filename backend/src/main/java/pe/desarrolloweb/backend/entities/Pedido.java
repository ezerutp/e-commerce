package pe.desarrolloweb.backend.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.desarrolloweb.backend.enums.EstadoPedido;

@Entity
@Table(name = "pedidos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_usuario"))
    private Usuario usuario;

    @NotBlank(message = "El número de orden es obligatorio")
    @Size(max = 80, message = "El número de orden no puede exceder 80 caracteres")
    @Column(name = "numero_orden", nullable = false, unique = true)
    private String numeroOrden;

    @NotBlank(message = "La moneda es obligatoria")
    @Size(max = 10, message = "La moneda no puede exceder 10 caracteres")
    @Column(nullable = false)
    private String moneda;

    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado;

    @NotNull(message = "El subtotal es obligatorio")
    @Digits(integer = 12, fraction = 2, message = "El subtotal debe tener un máximo de 12 enteros y 2 decimales")
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @NotNull(message = "Los impuestos son obligatorios")
    @Digits(integer = 12, fraction = 2, message = "Los impuestos deben tener un máximo de 12 enteros y 2 decimales")
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal impuestos;

    @NotNull(message = "El total es obligatorio")
    @Digits(integer = 12, fraction = 2, message = "El total debe tener un máximo de 12 enteros y 2 decimales")
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal total;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoPedido.PENDIENTE; 
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
