package pe.desarrolloweb.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.desarrolloweb.backend.entities.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
}