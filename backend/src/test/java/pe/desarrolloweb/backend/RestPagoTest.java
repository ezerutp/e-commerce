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
public class RestPagoTest {

	@Autowired
	private MockMvc mockMvc;

	// Obtener todos los pagos
	@Test
	public void testGetPagos() throws Exception {
		URI uri = new URI("/api/pagos");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);
	}

	// Pago con ID 9999 no existe
	@Test
	public void testGetPagoByIdNotExists() throws Exception {
		URI uri = new URI("/api/pagos/9999");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.NOT_FOUND.value(), status);
	}

	// Crear un nuevo pago
	@Test
	public void testCreatePago() throws Exception {
		URI uri = new URI("/api/pagos");
		String nuevoPagoJson = """
							{
				  "pedidoId": 101,
				  "metodo": "TARJETA",
				  "proveedor": "VISA",
				  "monto": 150.75,
				  "moneda": "PEN",
				  "estado": "PENDIENTE",
				  "referenciaProveedor": "AUTH123456",
				  "autorizadoEn": "2025-09-08T15:30:00",
				  "capturadoEn": "2025-09-08T16:00:00",
				  "updatedAt": "2025-09-08T21:45:00"
				}
							""";
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(nuevoPagoJson);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.CREATED.value(), status);
		String response = result.getResponse().getContentAsString();
		assertNotNull(response);
	}

	// Actualizar un pago que no existe
	@Test
	public void testUpdatePagoNotExists() throws Exception {
		URI uri = new URI("/api/pagos/9999");
		String updateJson = "{\"estado\":\"COMPLETADO\"}";
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(updateJson);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.NOT_FOUND.value(), status);
	}

	// Eliminar un pago que no existe
	@Test
	public void testDeletePagoNotExists() throws Exception {
		URI uri = new URI("/api/pagos/9999");
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(uri);
		MvcResult result = mockMvc.perform(request).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.NOT_FOUND.value(), status);
	}
}
