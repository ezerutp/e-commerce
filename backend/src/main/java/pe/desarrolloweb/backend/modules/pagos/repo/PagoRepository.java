package pe.desarrolloweb.backend.modules.pagos.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.desarrolloweb.backend.modules.pagos.domain.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
}