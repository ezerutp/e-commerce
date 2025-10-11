package pe.desarrolloweb.backend.modules.catalago.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.desarrolloweb.backend.modules.catalago.domain.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
}
