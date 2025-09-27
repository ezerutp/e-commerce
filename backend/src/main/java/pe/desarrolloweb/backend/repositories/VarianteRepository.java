package pe.desarrolloweb.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.desarrolloweb.backend.entities.Variante;

@Repository
public interface VarianteRepository extends JpaRepository<Variante, Long> {
    // Aquí puedes agregar métodos personalizados para consultas específicas
}
