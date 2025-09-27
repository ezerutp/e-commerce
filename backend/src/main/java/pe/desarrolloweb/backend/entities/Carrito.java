package pe.desarrolloweb.backend.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.desarrolloweb.backend.enums.EstadoCarrito;

@Entity
@Table(name = "carritos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "fk_carrito_usuario"))
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoCarrito estado = EstadoCarrito.ABIERTO;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarritoItem> items = new ArrayList<>();

    @Digits(integer = 12, fraction = 2)
    @Column(name = "subtotal", precision = 12, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Digits(integer = 12, fraction = 2)
    @Column(name = "descuento_total", precision = 12, scale = 2)
    private BigDecimal descuentoTotal = BigDecimal.ZERO;

    @Digits(integer = 12, fraction = 2)
    @Column(name = "impuestos", precision = 12, scale = 2)
    private BigDecimal impuestos = BigDecimal.ZERO;

    @Digits(integer = 12, fraction = 2)
    @Column(name = "total", precision = 12, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(length = 10)
    private String moneda = "PEN";

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void recalc() {
        this.subtotal = items.stream()
                .map(i -> i.getPrecioUnitario().multiply(new BigDecimal(i.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.total = subtotal.subtract(descuentoTotal == null ? BigDecimal.ZERO : descuentoTotal)
                .add(impuestos == null ? BigDecimal.ZERO : impuestos);
    }
}