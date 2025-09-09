package pe.desarrolloweb.backend.entities;

import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedido_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El pedidoId es obligatorio")
    @Column(name = "pedido_id", nullable = false)
    private Long pedidoId;

    @NotNull(message = "El varianteId es obligatorio")
    @Column(name = "variante_id", nullable = false)
    private Long varianteId;

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