package pe.desarrolloweb.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.desarrolloweb.backend.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
