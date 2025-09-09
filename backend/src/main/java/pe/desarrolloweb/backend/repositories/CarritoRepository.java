package pe.desarrolloweb.backend.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.desarrolloweb.backend.entities.*;
import pe.desarrolloweb.backend.enums.EstadoCarrito;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findFirstByUsuarioAndEstadoOrderByCreatedAtDesc(Usuario usuario, EstadoCarrito estado);
}