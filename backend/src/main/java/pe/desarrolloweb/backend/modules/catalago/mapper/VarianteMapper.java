package pe.desarrolloweb.backend.modules.catalago.mapper;

import java.math.BigDecimal;

import pe.desarrolloweb.backend.modules.catalago.domain.Producto;
import pe.desarrolloweb.backend.modules.catalago.domain.Variante;
import pe.desarrolloweb.backend.modules.catalago.web.dto.VarianteRequest;
import pe.desarrolloweb.backend.modules.catalago.web.dto.VarianteResponse;

public class VarianteMapper {

    public static Variante toEntity(VarianteRequest request) {
        if (request == null) {
            return null;
        }
        
        Variante variante = new Variante();
        
        Producto producto = new Producto();
        producto.setId(request.productoId());
        variante.setProducto(producto);
        
        variante.setSku(request.sku());
        variante.setPrecio(request.precio() != null ? request.precio() : BigDecimal.ZERO);
        variante.setPeso(request.peso() != null ? request.peso() : BigDecimal.ZERO);
        variante.setAtributosJson(request.atributosJson());
        variante.setActivo(request.activo());
        
        return variante;
    }
    
    public static VarianteResponse toResponse(Variante variante) {
        if (variante == null) {
            return null;
        }
        
        return new VarianteResponse(
            variante.getId(),
            variante.getProducto().getId(),
            variante.getProducto().getNombre(),
            variante.getSku(),
            variante.getPrecio(),
            variante.getPeso(),
            variante.getAtributosJson(),
            variante.isActivo(),
            variante.getUpdatedAt()
        );
    }
}
