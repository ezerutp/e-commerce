package pe.desarrolloweb.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

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
public class RestProductoCategoriaTest {

	@Autowired
	private MockMvc mockMvc;

	// Obtener todas las relaciones producto-categoría
	@Test
	public void testGetProductoCategorias() throws Exception {
		URI uri = new URI("/api/producto-categorias");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.FORBIDDEN.value(), status);
	}

	// Relación producto-categoría con ID 9999 no existe
	@Test
	public void testGetProductoCategoriaByIdNotExists() throws Exception {
		URI uri = new URI("/api/producto-categorias/9999");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.FORBIDDEN.value(), status);
	}

	// Eliminar una relación producto-categoría con ID 9999 que no existe
	@Test
	public void testDeleteProductoCategoriaNotExists() throws Exception {
		URI uri = new URI("/api/producto-categorias/9999");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(uri);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.FORBIDDEN.value(), status);
	}
}
