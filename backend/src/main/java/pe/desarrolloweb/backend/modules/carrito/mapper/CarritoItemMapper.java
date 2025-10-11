package pe.desarrolloweb.backend.modules.carrito.mapper;

import pe.desarrolloweb.backend.modules.carrito.domain.CarritoItem;
import pe.desarrolloweb.backend.modules.carrito.web.dto.CarritoItemResponse;

public class CarritoItemMapper {
    
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
