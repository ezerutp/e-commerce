package pe.desarrolloweb.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class RestUsuarioTest {

    @Autowired
    private MockMvc mockMvc;
    private String token;

    // Generar el token JWT para los tests
    @BeforeEach
    public void setUp() throws Exception {
        String loginJson = """
                {
                "username": "admin",
                "password": "admin123"
                }
                """;
        MvcResult res = mockMvc.perform(
                MockMvcRequestBuilders.post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andReturn();
        token = res.getResponse().getContentAsString();
    }

    // Obtener todos los usuarios
    @Test
    public void testGetUsuarios() throws Exception {
        URI uri = new URI("/api/usuarios");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        int status = result.getResponse().getStatus();
        // Verificar el estado de la respuesta (Espera 200)
        assertEquals(HttpStatus.OK.value(), status);
    }

    // Usuario con ID 34 no existe
    @Test
    public void testGetUsuarioByIdExists() throws Exception {
        URI uri = new URI("/api/usuarios/34");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        int status = result.getResponse().getStatus();
        // Verifica si el código de estado (Espera 404)
        assertEquals(HttpStatus.NOT_FOUND.value(), status);
    }

    // Usuario con ID 1 sí existe
    @Test
    public void testGetUsuarioByIdNotExists() throws Exception {
        URI uri = new URI("/api/usuarios/1");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        int status = result.getResponse().getStatus();
        // Verificar el estado de la respuesta (Espera 200)
        assertEquals(HttpStatus.OK.value(), status);
    }

    // Crear un nuevo usuario)
    @Test
    public void testCreateUsuario() throws Exception {
        URI uri = new URI("/api/usuarios");
        String nuevoUsuarioJson = """
                {
                "email": "juan.david.teco@email.com",
                "username": "juandavidteco",
                "password": "usuario123",
                "nombre": "Juan",
                "apellido": "Pérez",
                "telefono": "987654323",
                "estado": "ACTIVO",
                "rol": "USUARIO"
                }
                """;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(nuevoUsuarioJson);
        MvcResult result = mockMvc.perform(request).andReturn();
        int status = result.getResponse().getStatus();
        // Verificar el estado de la respuesta (Espera 201)
        assertEquals(HttpStatus.CREATED.value(), status);
    }

    // Eliminar un usuario con ID 432 que no existe
    @Test
    public void testDeleteUsuarioNotExists() throws Exception {
        URI uri = new URI("/api/usuarios/432");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(uri)
                .header("Authorization", "Bearer " + token);
        MvcResult result = mockMvc.perform(request).andReturn();
        int status = result.getResponse().getStatus();
        // Verificar el estado de la respuesta (Espera 404)
        assertEquals(HttpStatus.NOT_FOUND.value(), status);
    }
}
