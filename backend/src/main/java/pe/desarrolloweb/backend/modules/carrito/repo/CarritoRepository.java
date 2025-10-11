package pe.desarrolloweb.backend.modules.carrito.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.desarrolloweb.backend.modules.carrito.domain.Carrito;
import pe.desarrolloweb.backend.modules.usuarios.domain.Usuario;
import pe.desarrolloweb.backend.shared.enums.EstadoCarrito;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findFirstByUsuarioAndEstadoOrderByCreatedAtDesc(Usuario usuario, EstadoCarrito estado);
}