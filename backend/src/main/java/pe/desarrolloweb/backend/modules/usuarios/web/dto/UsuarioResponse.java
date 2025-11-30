package pe.desarrolloweb.backend.modules.usuarios.web.dto;

import java.time.LocalDateTime;

import pe.desarrolloweb.backend.shared.enums.EstadoUsuario;
import pe.desarrolloweb.backend.shared.enums.RolUsuario;

public record UsuarioResponse(
    Long id,
    String email,
    String username,
    String nombre,
    String apellido,
    String telefono,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    EstadoUsuario estado,
    RolUsuario rol ) {
}
