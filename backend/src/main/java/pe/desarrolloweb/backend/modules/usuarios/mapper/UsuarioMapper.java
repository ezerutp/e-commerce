package pe.desarrolloweb.backend.modules.usuarios.mapper;

import pe.desarrolloweb.backend.modules.usuarios.domain.Usuario;
import pe.desarrolloweb.backend.modules.usuarios.web.dto.UsuarioResponse;

public class UsuarioMapper {
     public static UsuarioResponse toResponse(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        return new UsuarioResponse(
            usuario.getId(),
            usuario.getEmail(),
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