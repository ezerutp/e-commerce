package pe.desarrolloweb.backend.modules.pedidos.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.desarrolloweb.backend.modules.pedidos.domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
}
