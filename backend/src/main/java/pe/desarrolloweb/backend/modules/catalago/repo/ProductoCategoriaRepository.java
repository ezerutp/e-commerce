package pe.desarrolloweb.backend.modules.catalago.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.desarrolloweb.backend.modules.catalago.domain.Categoria;
import pe.desarrolloweb.backend.modules.catalago.domain.Producto;
import pe.desarrolloweb.backend.modules.catalago.domain.ProductoCategoria;

public interface ProductoCategoriaRepository extends JpaRepository<ProductoCategoria, Long> {
    List<ProductoCategoria> findByProducto(Producto producto);
    List<ProductoCategoria> findByCategoria(Categoria categoria);
    boolean existsByProductoIdAndCategoriaId(Long productoId, Long categoriaId);
    void deleteByProductoIdAndCategoriaId(Long productoId, Long categoriaId);
}