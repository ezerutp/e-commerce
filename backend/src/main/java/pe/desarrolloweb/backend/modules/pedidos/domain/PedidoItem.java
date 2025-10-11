package pe.desarrolloweb.backend.modules.pedidos.domain;

import java.math.BigDecimal;

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
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.desarrolloweb.backend.modules.catalago.domain.Variante;

@Entity
@Table(name = "pedido_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El pedido es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "pedido_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_item_pedido"))
    private Pedido pedido;

    @NotNull(message = "El variante es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "variante_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedidoitem_variante"))
    private Variante variante;

    @Size(max = 120, message = "El SKU no puede exceder 120 caracteres")
    @Column(name = "sku_snapshot")
    private String skuSnapshot;

    @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
    @Column(name = "nombre_producto_snapshot")
    private String nombreProductoSnapshot;

    @DecimalMin(value = "0.00", message = "El precio unitario no puede ser negativo")
    @Column(name = "precio_unitario", nullable = false)
    private BigDecimal precioUnitario;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Column(nullable = false)
    private Integer cantidad;
}