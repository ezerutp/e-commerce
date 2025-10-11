package pe.desarrolloweb.backend.modules.catalago.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import pe.desarrolloweb.backend.modules.catalago.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findBySlug(String slug);
    boolean existsByNombre(String nombre);
}