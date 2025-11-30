package pe.desarrolloweb.backend.modules.catalago.mapper;

import pe.desarrolloweb.backend.modules.catalago.domain.Categoria;
import pe.desarrolloweb.backend.modules.catalago.domain.Producto;
import pe.desarrolloweb.backend.modules.catalago.domain.ProductoCategoria;
import pe.desarrolloweb.backend.modules.catalago.web.dto.ProductoCategoriaRequest;
import pe.desarrolloweb.backend.modules.catalago.web.dto.ProductoCategoriaResponse;

public class ProductoCategoriaMapper {

    public static ProductoCategoria toEntity(ProductoCategoriaRequest request) {
        if (request == null) {
            return null;
        }
        
        ProductoCategoria productoCategoria = new ProductoCategoria();
        
        Producto producto = new Producto();
        producto.setId(request.productoId());
        productoCategoria.setProducto(producto);
        
        Categoria categoria = new Categoria();
        categoria.setId(request.categoriaId());
        productoCategoria.setCategoria(categoria);
        
        return productoCategoria;
    }
    
    public static ProductoCategoriaResponse toResponse(ProductoCategoria productoCategoria) {
        if (productoCategoria == null) {
            return null;
        }
        
        return new ProductoCategoriaResponse(
            productoCategoria.getId(),
            productoCategoria.getProducto().getId(),
            productoCategoria.getProducto().getNombre(),
            productoCategoria.getCategoria().getId(),
            productoCategoria.getCategoria().getNombre(),
            productoCategoria.getCreatedAt(),
            productoCategoria.getUpdatedAt()
        );
    }
}
