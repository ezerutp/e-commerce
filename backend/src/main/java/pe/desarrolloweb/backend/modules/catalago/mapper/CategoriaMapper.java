package pe.desarrolloweb.backend.modules.catalago.mapper;

import pe.desarrolloweb.backend.modules.catalago.domain.Categoria;
import pe.desarrolloweb.backend.modules.catalago.web.dto.CategoriaRequest;
import pe.desarrolloweb.backend.modules.catalago.web.dto.CategoriaResponse;

public class CategoriaMapper {

    public static Categoria toEntity(CategoriaRequest request) {
        if (request == null) {
            return null;
        }
        
        Categoria categoria = new Categoria();
        categoria.setNombre(request.nombre());
        categoria.setDescripcion(request.descripcion());
        categoria.setSlug(request.slug());
        
        return categoria;
    }
    
    public static CategoriaResponse toResponse(Categoria categoria) {
        if (categoria == null) {
            return null;
        }
        
        return new CategoriaResponse(
            categoria.getId(),
            categoria.getNombre(),
            categoria.getDescripcion(),
            categoria.getSlug(),
            categoria.getCreatedAt(),
            categoria.getUpdatedAt()
        );
    }
}
