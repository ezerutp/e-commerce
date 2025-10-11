package pe.desarrolloweb.backend.modules.usuarios.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.desarrolloweb.backend.modules.usuarios.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
