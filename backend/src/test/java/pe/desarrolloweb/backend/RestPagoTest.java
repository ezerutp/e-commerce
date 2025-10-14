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
public class RestPagoTest {

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

	// Obtener todos los pagos
	@Test
	public void testGetPagos() throws Exception {
		URI uri = new URI("/api/pagos");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri)
				.header("Authorization", "Bearer " + token)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
	}

	// Pago con ID 9999 no existe
	@Test
	public void testGetPagoByIdNotExists() throws Exception {
		URI uri = new URI("/api/pagos/9999");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri)
				.header("Authorization", "Bearer " + token)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.NOT_FOUND.value(), status);
	}

	// Eliminar un pago que no existe
	@Test
	public void testDeletePagoNotExists() throws Exception {
		URI uri = new URI("/api/pagos/9999");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(uri)
				.header("Authorization", "Bearer " + token);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.NOT_FOUND.value(), status);
	}
}
