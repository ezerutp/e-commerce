package pe.desarrolloweb.backend.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

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
