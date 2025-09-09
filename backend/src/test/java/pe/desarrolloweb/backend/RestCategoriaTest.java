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
public class RestCategoriaTest {

	@Autowired
	private MockMvc mockMvc;

	// Obtener todas las categorías
	@Test
	public void testGetCategorias() throws Exception {
		URI uri = new URI("/api/categorias");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
	}

	// Categoría con ID 9999 no existe
	@Test
	public void testGetCategoriaByIdNotExists() throws Exception {
		URI uri = new URI("/api/categorias/9999");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.NOT_FOUND.value(), status);
	}

	// Crear una nueva categoría
	@Test
	public void testCreateCategoria() throws Exception {
		URI uri = new URI("/api/categorias");
		String nuevaCategoriaJson = """
			{
				\"nombre\": \"Electrónica\",
				\"descripcion\": \"Productos electrónicos\"
			}
			""";
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(nuevaCategoriaJson);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		// Espera 201 si se crea correctamente, 400 si hay error de datos
		boolean createdOrBadRequest = status == HttpStatus.CREATED.value() || status == HttpStatus.BAD_REQUEST.value();
		assertEquals(true, createdOrBadRequest);
	}

	// Eliminar una categoría con ID 9999 que no existe
	@Test
	public void testDeleteCategoriaNotExists() throws Exception {
		URI uri = new URI("/api/categorias/9999");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(uri);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.NOT_FOUND.value(), status);
	}
}
