package pe.desarrolloweb.backend.modules.catalago.mapper;

import pe.desarrolloweb.backend.modules.catalago.domain.Producto;
import pe.desarrolloweb.backend.modules.catalago.web.dto.ProductoRequest;
import pe.desarrolloweb.backend.modules.catalago.web.dto.ProductoResponse;

public class ProductoMapper {

    public static Producto toEntity(ProductoRequest request) {
        if (request == null) {
            return null;
        }
        
        Producto producto = new Producto();
        producto.setNombre(request.nombre());
        producto.setDescripcion(request.descripcion());
        producto.setMarca(request.marca());
        producto.setActivo(request.activo() != null ? request.activo() : true);
        producto.setSlug(request.slug());
        
        return producto;
    }
    
    public static ProductoResponse toResponse(Producto producto) {
        if (producto == null) {
            return null;
        }
        
        return new ProductoResponse(
            producto.getId(),
            producto.getNombre(),
            producto.getDescripcion(),
            producto.getMarca(),
            producto.getActivo(),
            producto.getSlug(),
            producto.getCreatedAt(),
            producto.getUpdatedAt()
        );
    }
}
