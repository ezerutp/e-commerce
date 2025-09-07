package pe.desarrolloweb.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.desarrolloweb.backend.entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
}
