package pe.desarrolloweb.backend.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.desarrolloweb.backend.entities.Usuario;
import pe.desarrolloweb.backend.security.LoginRequest;
import pe.desarrolloweb.backend.security.jwt.JwtUtil;
import pe.desarrolloweb.backend.services.UsuarioService;


@RestController
@RequiredArgsConstructor
public class AuthRestController {
    
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/auth")
	public ResponseEntity<Map<String, String>> authenticate(@RequestBody LoginRequest authRequest) {
        Usuario user = usuarioService.findByUsername(authRequest.getUsername());
		if (user != null && passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
			return ResponseEntity.ok(Map.of("token", jwtUtil.generateToken(user.getUsername())));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
