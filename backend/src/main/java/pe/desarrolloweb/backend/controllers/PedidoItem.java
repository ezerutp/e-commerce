package pe.desarrolloweb.backend.entities;

import java.math.BigDecimal;

@Entity
@Table(name = "pedido_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItem {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pedido_id", nullable = false)
    private Pedido pedido;
    @Column(name = "variante_id", nullable = false)
    private Long variante;
    private String sku_snapshot;
    private String nombre_producto_snapshot;
    private BigDecimal precio_unitario;
    private Integer cantidad;

}
