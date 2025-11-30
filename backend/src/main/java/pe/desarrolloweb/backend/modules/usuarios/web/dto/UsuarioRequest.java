package pe.desarrolloweb.backend.modules.usuarios.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import pe.desarrolloweb.backend.shared.enums.EstadoUsuario;
import pe.desarrolloweb.backend.shared.enums.RolUsuario;

public record UsuarioRequest(
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    @Size(max = 255, message = "El email no puede exceder 255 caracteres")
    String email,

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(max = 50, message = "El nombre de usuario no puede exceder 50 caracteres")
    String username,
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(max = 255, message = "La contraseña no puede exceder 255 caracteres")
    String password,
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120, message = "El nombre no puede exceder 120 caracteres")
    String nombre,
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 120, message = "El apellido no puede exceder 120 caracteres")
    String apellido,
    
    @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe tener 9 dígitos")
    @Size(max = 50, message = "El teléfono no puede exceder 50 caracteres")
    String telefono,
    
    @NotNull(message = "El estado es obligatorio")
    EstadoUsuario estado,
    
    @NotNull(message = "El rol es obligatorio")
    RolUsuario rol
) {
}
