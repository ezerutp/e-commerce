package pe.desarrolloweb.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.desarrolloweb.backend.entities.CarritoItem;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {
    
}
