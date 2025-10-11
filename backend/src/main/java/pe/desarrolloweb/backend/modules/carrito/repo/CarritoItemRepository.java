package pe.desarrolloweb.backend.modules.carrito.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.desarrolloweb.backend.modules.carrito.domain.CarritoItem;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {
    
}
