package pe.desarrolloweb.backend.modules.usuarios.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pe.desarrolloweb.backend.modules.usuarios.domain.Usuario;
import pe.desarrolloweb.backend.modules.usuarios.mapper.UsuarioMapper;
import pe.desarrolloweb.backend.modules.usuarios.service.UsuarioService;
import pe.desarrolloweb.backend.modules.usuarios.web.dto.UsuarioRequest;
import pe.desarrolloweb.backend.modules.usuarios.web.dto.UsuarioResponse;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    // Obtener todos los usuarios
    @GetMapping
    public List<UsuarioResponse> getAllUsuarios() {
        return usuarioService.findAll().stream()
                .map(UsuarioMapper::toResponse)
                .toList();
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> getUsuarioById(@PathVariable("id") Long id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        if (usuario.isPresent()) {
            UsuarioResponse response = UsuarioMapper.toResponse(usuario.get());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<UsuarioResponse> createUsuario(@Valid @RequestBody UsuarioRequest request) {
        Usuario usuario = UsuarioMapper.toEntity(request);
        Usuario nuevoUsuario = usuarioService.save(usuario);
        UsuarioResponse response = UsuarioMapper.toResponse(nuevoUsuario);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Actualizar un usuario existente
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponse> updateUsuario(@PathVariable("id") Long id, @Valid @RequestBody UsuarioRequest request) {
        Optional<Usuario> usuarioExistente = usuarioService.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            
            // Actualizar solo los campos que no son null
            if (request.email() != null) {
                usuario.setEmail(request.email());
            }
            if (request.username() != null) {
                usuario.setUsername(request.username());
            }
            if (request.password() != null) {
                usuario.setPassword(request.password());
            }
            if (request.nombre() != null) {
                usuario.setNombre(request.nombre());
            }
            if (request.apellido() != null) {
                usuario.setApellido(request.apellido());
            }
            if (request.telefono() != null) {
                usuario.setTelefono(request.telefono());
            }
            if (request.estado() != null) {
                usuario.setEstado(request.estado());
            }
            if (request.rol() != null) {
                usuario.setRol(request.rol());
            }
            
            Usuario usuarioActualizado = usuarioService.save(usuario);
            UsuarioResponse response = UsuarioMapper.toResponse(usuarioActualizado);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable("id") Long id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        if (usuario.isPresent()) {
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
