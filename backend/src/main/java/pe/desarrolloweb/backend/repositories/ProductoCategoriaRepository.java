package pe.desarrolloweb.backend.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.desarrolloweb.backend.entities.*;

public interface ProductoCategoriaRepository extends JpaRepository<ProductoCategoria, Long> {
    List<ProductoCategoria> findByProducto(Producto producto);
    List<ProductoCategoria> findByCategoria(Categoria categoria);
    boolean existsByProductoIdAndCategoriaId(Long productoId, Long categoriaId);
    void deleteByProductoIdAndCategoriaId(Long productoId, Long categoriaId);
}