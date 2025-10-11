package pe.desarrolloweb.backend.modules.catalago.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.desarrolloweb.backend.modules.catalago.domain.Variante;

@Repository
public interface VarianteRepository extends JpaRepository<Variante, Long> {
    // Aquí puedes agregar métodos personalizados para consultas específicas
}
