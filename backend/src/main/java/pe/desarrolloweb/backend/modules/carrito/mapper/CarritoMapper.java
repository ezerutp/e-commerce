package pe.desarrolloweb.backend.modules.carrito.mapper;

import pe.desarrolloweb.backend.modules.carrito.domain.Carrito;
import pe.desarrolloweb.backend.modules.carrito.web.dto.CarritoResponse;

public class CarritoMapper {
    
    public static CarritoResponse toResponse(Carrito carrito) {
        if (carrito == null) {
            return null;
        }
        
        return new CarritoResponse(
            carrito.getId(),
            carrito.getUsuario().getId(),
            carrito.getEstado(),
            carrito.getSubtotal(),
            carrito.getDescuentoTotal(),
            carrito.getImpuestos(),
            carrito.getTotal(),
            carrito.getMoneda(),
            carrito.getCreatedAt(),
            carrito.getUpdatedAt(),
            carrito.getItems().stream()
                .map(CarritoItemMapper::toResponse)
                .toList()
        );
    }
}
