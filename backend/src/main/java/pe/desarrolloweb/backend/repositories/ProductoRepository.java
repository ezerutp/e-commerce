package pe.desarrolloweb.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.desarrolloweb.backend.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
}
