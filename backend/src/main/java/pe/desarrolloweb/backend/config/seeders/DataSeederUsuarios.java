package pe.desarrolloweb.backend.config.seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import pe.desarrolloweb.backend.modules.usuarios.domain.Usuario;
import pe.desarrolloweb.backend.modules.usuarios.repo.UsuarioRepository;
import pe.desarrolloweb.backend.shared.enums.EstadoUsuario;
import pe.desarrolloweb.backend.shared.enums.RolUsuario;

@Component
@Order(1)
public class DataSeederUsuarios implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    public DataSeederUsuarios(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) {
        seedUsuarios();
    }

    private void seedUsuarios() {
        if (usuarioRepository.count() == 0) {
            System.out.println("crea");
            // Usuario 1 - Administrador
            Usuario admin = new Usuario();
            admin.setEmail("admin@ecommerce.com");
            admin.setPasswordHash("admin123");
            admin.setNombre("Carlos");
            admin.setApellido("Administrador");
            admin.setTelefono("987654321");
            admin.setEstado(EstadoUsuario.ACTIVO);
            admin.setRol(RolUsuario.ADMIN);
            usuarioRepository.save(admin);

            // Usuario 2 - Moderador
            Usuario moderador = new Usuario();
            moderador.setEmail("moderador@ecommerce.com");
            moderador.setPasswordHash("moderador123");
            moderador.setNombre("Ana");
            moderador.setApellido("Moderadora");
            moderador.setTelefono("987654322");
            moderador.setEstado(EstadoUsuario.ACTIVO);
            moderador.setRol(RolUsuario.MODERADOR);
            usuarioRepository.save(moderador);

            // Usuario 3 - Usuario simple activo
            Usuario usuario1 = new Usuario();
            usuario1.setEmail("juan.perez@email.com");
            usuario1.setPasswordHash("usuario123");
            usuario1.setNombre("Juan");
            usuario1.setApellido("Pérez");
            usuario1.setTelefono("987654323");
            usuario1.setEstado(EstadoUsuario.ACTIVO);
            usuario1.setRol(RolUsuario.USUARIO);
            usuarioRepository.save(usuario1);

            // Usuario 4 - Usuario simple y suspendido
            Usuario usuario2 = new Usuario();
            usuario2.setEmail("maria.garcia@email.com");
            usuario2.setPasswordHash("usuario456");
            usuario2.setNombre("María");
            usuario2.setApellido("García");
            usuario2.setTelefono("987654324");
            usuario2.setEstado(EstadoUsuario.SUSPENDIDO);
            usuario2.setRol(RolUsuario.USUARIO);
            usuarioRepository.save(usuario2);
        }
    }
}