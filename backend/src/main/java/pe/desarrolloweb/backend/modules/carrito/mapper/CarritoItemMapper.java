package pe.desarrolloweb.backend.modules.carrito.mapper;

import pe.desarrolloweb.backend.modules.carrito.domain.Carrito;
import pe.desarrolloweb.backend.modules.carrito.domain.CarritoItem;
import pe.desarrolloweb.backend.modules.carrito.web.dto.CarritoItemRequest;
import pe.desarrolloweb.backend.modules.carrito.web.dto.CarritoItemResponse;
import pe.desarrolloweb.backend.modules.catalago.domain.Variante;

public class CarritoItemMapper {

    public static CarritoItem toEntity(CarritoItemRequest request) {
        if (request == null) {
            return null;
        }
        
        CarritoItem item = new CarritoItem();

        Carrito carrito = new Carrito();
        carrito.setId(request.carritoId());
        item.setCarrito(carrito);

        Variante variante = new Variante();
        variante.setId(request.varianteId());
        item.setVariante(variante);
        
        item.setCantidad(request.cantidad());
        item.setPrecioUnitario(request.precioUnitario());
        return item;
    }
    
    public static CarritoItemResponse toResponse(CarritoItem item) {
        if (item == null) {
            return null;
        }
        
        return new CarritoItemResponse(
            item.getId(),
            item.getCarrito().getId(),
            item.getVariante().getId(),
            item.getCantidad(),
            item.getPrecioUnitario(),
            item.getUpdatedAt()
        );
    }
}
