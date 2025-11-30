package pe.desarrolloweb.backend.modules.pedidos.mapper;

import pe.desarrolloweb.backend.modules.catalago.domain.Variante;
import pe.desarrolloweb.backend.modules.pedidos.domain.Pedido;
import pe.desarrolloweb.backend.modules.pedidos.domain.PedidoItem;
import pe.desarrolloweb.backend.modules.pedidos.web.dto.PedidoItemRequest;
import pe.desarrolloweb.backend.modules.pedidos.web.dto.PedidoItemResponse;

public class PedidoItemMapper {

    public static PedidoItem toEntity(PedidoItemRequest request) {
        if (request == null) {
            return null;
        }
        
        PedidoItem pedidoItem = new PedidoItem();
        
        Pedido pedido = new Pedido();
        pedido.setId(request.pedidoId());
        pedidoItem.setPedido(pedido);
        
        Variante variante = new Variante();
        variante.setId(request.varianteId());
        pedidoItem.setVariante(variante);
        
        pedidoItem.setSkuSnapshot(request.skuSnapshot());
        pedidoItem.setNombreProductoSnapshot(request.nombreProductoSnapshot());
        pedidoItem.setPrecioUnitario(request.precioUnitario());
        pedidoItem.setCantidad(request.cantidad());
        
        return pedidoItem;
    }
    
    public static PedidoItemResponse toResponse(PedidoItem pedidoItem) {
        if (pedidoItem == null) {
            return null;
        }
        
        return new PedidoItemResponse(
            pedidoItem.getId(),
            pedidoItem.getPedido().getId(),
            pedidoItem.getVariante().getId(),
            pedidoItem.getSkuSnapshot(),
            pedidoItem.getNombreProductoSnapshot(),
            pedidoItem.getPrecioUnitario(),
            pedidoItem.getCantidad()
        );
    }
}
