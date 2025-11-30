package pe.desarrolloweb.backend.modules.usuarios.mapper;

import pe.desarrolloweb.backend.modules.usuarios.domain.Usuario;
import pe.desarrolloweb.backend.modules.usuarios.web.dto.UsuarioRequest;
import pe.desarrolloweb.backend.modules.usuarios.web.dto.UsuarioResponse;
import pe.desarrolloweb.backend.shared.enums.EstadoUsuario;
import pe.desarrolloweb.backend.shared.enums.RolUsuario;

public class UsuarioMapper {
    
    public static Usuario toEntity(UsuarioRequest request) {
        if (request == null) {
            return null;
        }
        
        Usuario usuario = new Usuario();
        usuario.setEmail(request.email());
        usuario.setUsername(request.username());
        usuario.setPassword(request.password());
        usuario.setNombre(request.nombre());
        usuario.setApellido(request.apellido());
        usuario.setTelefono(request.telefono());
        usuario.setEstado(request.estado() != null ? request.estado() : EstadoUsuario.ACTIVO);
        usuario.setRol(request.rol() != null ? request.rol() : RolUsuario.USUARIO);
        
        return usuario;
    }
    
    public static UsuarioResponse toResponse(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        return new UsuarioResponse(
            usuario.getId(),
            usuario.getEmail(),
            usuario.getUsername(),
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getTelefono(),
            usuario.getCreatedAt(),
            usuario.getUpdatedAt(),
            usuario.getEstado(),
            usuario.getRol()
        );
    }
} 