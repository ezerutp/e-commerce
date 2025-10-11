package pe.desarrolloweb.backend.modules.pagos.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.desarrolloweb.backend.modules.pedidos.domain.Pedido;
import pe.desarrolloweb.backend.shared.enums.EstadoPago;
import pe.desarrolloweb.backend.shared.enums.MetodoPago;


@Entity
@Table(name = "pagos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El pedido es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "pedido_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pago_pedido"))
    private Pedido pedido;

    @NotNull(message = "El método de pago es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoPago metodo;

    @Size(max = 120, message = "El proveedor no puede exceder 120 caracteres")
    private String proveedor;

    @DecimalMin(value = "0.00", message = "El monto no puede ser negativo")
    private BigDecimal monto;

    @Size(max = 10, message = "La moneda no puede exceder 10 caracteres")
    private String moneda;

    @NotNull(message = "El estado del pago es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPago estado;

    @Size(max = 255, message = "La referencia no puede exceder 255 caracteres")
    @Column(name = "referencia_proveedor")
    private String referenciaProveedor;

    @Column(name = "autorizado_en")
    private LocalDateTime autorizadoEn;

    @Column(name = "capturado_en")
    private LocalDateTime capturadoEn;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}