package pe.desarrolloweb.backend.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.desarrolloweb.backend.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findBySlug(String slug);
    boolean existsByNombre(String nombre);
}