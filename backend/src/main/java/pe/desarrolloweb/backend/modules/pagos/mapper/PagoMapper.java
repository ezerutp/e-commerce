package pe.desarrolloweb.backend.modules.pagos.mapper;

import pe.desarrolloweb.backend.modules.pagos.domain.Pago;
import pe.desarrolloweb.backend.modules.pagos.web.dto.PagoRequest;
import pe.desarrolloweb.backend.modules.pagos.web.dto.PagoResponse;
import pe.desarrolloweb.backend.modules.pedidos.domain.Pedido;

public class PagoMapper {

    public static Pago toEntity(PagoRequest request) {
        if (request == null) {
            return null;
        }
        
        Pago pago = new Pago();
        
        Pedido pedido = new Pedido();
        pedido.setId(request.pedidoId());
        pago.setPedido(pedido);
        
        pago.setMetodo(request.metodo());
        pago.setProveedor(request.proveedor());
        pago.setMonto(request.monto());
        pago.setMoneda(request.moneda());
        pago.setEstado(request.estado());
        pago.setReferenciaProveedor(request.referenciaProveedor());
        pago.setAutorizadoEn(request.autorizadoEn());
        pago.setCapturadoEn(request.capturadoEn());
        
        return pago;
    }
    
    public static PagoResponse toResponse(Pago pago) {
        if (pago == null) {
            return null;
        }
        
        return new PagoResponse(
            pago.getId(),
            pago.getPedido().getId(),
            pago.getMetodo(),
            pago.getProveedor(),
            pago.getMonto(),
            pago.getMoneda(),
            pago.getEstado(),
            pago.getReferenciaProveedor(),
            pago.getAutorizadoEn(),
            pago.getCapturadoEn(),
            pago.getUpdatedAt()
        );
    }
}
