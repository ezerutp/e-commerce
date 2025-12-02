package pe.desarrolloweb.backend.modules.usuarios.web;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.desarrolloweb.backend.config.security.JwtUtil;
import pe.desarrolloweb.backend.modules.usuarios.domain.Usuario;
import pe.desarrolloweb.backend.modules.usuarios.service.UsuarioService;
import pe.desarrolloweb.backend.modules.usuarios.web.dto.AuthRequest;

@RestController
@RequiredArgsConstructor
public class AuthController {

        private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/auth")
	public ResponseEntity<Map<String, String>> authenticate(@RequestBody AuthRequest authRequest) {
        String username = authRequest.username();
        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Optional<Usuario> user = usuarioService.findByUsername(username);
		if (user.isPresent() && passwordEncoder.matches(authRequest.password(), user.get().getPassword())) {
			return ResponseEntity.ok(Map.of(
                "token", jwtUtil.generateToken(user.get().getUsername()),
                "rol", user.get().getRol().toString()
            ));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
