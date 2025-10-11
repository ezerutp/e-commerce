package pe.desarrolloweb.backend.modules.pedidos.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.desarrolloweb.backend.modules.pedidos.domain.PedidoItem;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
}
