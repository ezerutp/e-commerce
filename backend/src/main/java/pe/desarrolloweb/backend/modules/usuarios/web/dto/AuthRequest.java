package pe.desarrolloweb.backend.modules.usuarios.web.dto;

public record AuthRequest(
    String username,
    String password
) {}
    