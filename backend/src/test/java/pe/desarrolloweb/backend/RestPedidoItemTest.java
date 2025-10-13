package pe.desarrolloweb.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
public class RestPedidoItemTest {

	@Autowired
	private MockMvc mockMvc;

	// Obtener todos los pedido items
	@Test
	public void testGetPedidoItems() throws Exception {
		URI uri = new URI("/api/pedido-items");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.FORBIDDEN.value(), status);
	}

	// PedidoItem con ID 9999 no existe
	@Test
	public void testGetPedidoItemByIdNotExists() throws Exception {
		URI uri = new URI("/api/pedido-items/9999");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.FORBIDDEN.value(), status);
	}

	// Crear un nuevo pedido item
	@Test
	public void testCreatePedidoItem() throws Exception {
		URI uri = new URI("/api/pedido-items");
		String nuevoPedidoItemJson = """
			{
			"pedido": { "id": 1 },
			"variante": { "id": 1 },
			"skuSnapshot": "SKU12345",
			"nombreProductoSnapshot": "Producto de prueba",
			"precioUnitario": 50.00,
			"cantidad": 2
			}
			""";
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(nuevoPedidoItemJson);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.FORBIDDEN.value(), status);
		String response = result.getResponse().getContentAsString();
		assertNotNull(response);
	}

	// Actualizar un pedido item que no existe
	@Test
	public void testUpdatePedidoItemNotExists() throws Exception {
		URI uri = new URI("/api/pedido-items/9999");
		String updateJson = "{\"cantidad\":5}";
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(updateJson);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.FORBIDDEN.value(), status);
	}

	// Eliminar un pedido item que no existe
	@Test
	public void testDeletePedidoItemNotExists() throws Exception {
		URI uri = new URI("/api/pedido-items/9999");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(uri);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.FORBIDDEN.value(), status);
	}
}
