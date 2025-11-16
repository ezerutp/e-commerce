package pe.desarrolloweb.backend.modules.carrito.mapper;

import pe.desarrolloweb.backend.modules.carrito.domain.Carrito;
import pe.desarrolloweb.backend.modules.carrito.web.dto.CarritoRequest;
import pe.desarrolloweb.backend.modules.carrito.web.dto.CarritoResponse;
import pe.desarrolloweb.backend.modules.usuarios.domain.Usuario;

public class CarritoMapper {

    public static Carrito toEntity(CarritoRequest request) {
        if (request == null) {
            return null;
        }
        
        Carrito carrito = new Carrito();

        Usuario usuario = new Usuario();
        usuario.setId(request.usuarioId());
        carrito.setUsuario(usuario);

        carrito.setEstado(request.estado());
        carrito.setSubtotal(request.subtotal());
        carrito.setDescuentoTotal(request.descuentoTotal());
        carrito.setImpuestos(request.impuestos());
        carrito.setTotal(request.total());
        carrito.setMoneda(request.moneda());
        return carrito;
    }
    
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
