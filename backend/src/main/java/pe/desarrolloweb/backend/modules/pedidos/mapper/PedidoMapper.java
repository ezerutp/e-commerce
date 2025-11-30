package pe.desarrolloweb.backend.modules.pedidos.mapper;

import pe.desarrolloweb.backend.modules.pedidos.domain.Pedido;
import pe.desarrolloweb.backend.modules.pedidos.web.dto.PedidoRequest;
import pe.desarrolloweb.backend.modules.pedidos.web.dto.PedidoResponse;
import pe.desarrolloweb.backend.modules.usuarios.domain.Usuario;

public class PedidoMapper {

    public static Pedido toEntity(PedidoRequest request) {
        if (request == null) {
            return null;
        }
        
        Pedido pedido = new Pedido();
        
        Usuario usuario = new Usuario();
        usuario.setId(request.usuarioId());
        pedido.setUsuario(usuario);
        
        pedido.setNumeroOrden(request.numeroOrden());
        pedido.setMoneda(request.moneda());
        pedido.setEstado(request.estado());
        pedido.setSubtotal(request.subtotal());
        pedido.setImpuestos(request.impuestos());
        pedido.setTotal(request.total());
        
        return pedido;
    }
    
    public static PedidoResponse toResponse(Pedido pedido) {
        if (pedido == null) {
            return null;
        }
        
        return new PedidoResponse(
            pedido.getId(),
            pedido.getUsuario().getId(),
            pedido.getNumeroOrden(),
            pedido.getMoneda(),
            pedido.getEstado(),
            pedido.getSubtotal(),
            pedido.getImpuestos(),
            pedido.getTotal(),
            pedido.getCreatedAt(),
            pedido.getUpdatedAt()
        );
    }
}
