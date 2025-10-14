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
public class RestPedidoTest {

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

    // Obtener todos los pedidos
    @Test
    public void testGetPedidos() throws Exception {
        URI uri = new URI("/api/pedidos");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    // Pedido con ID 555 no existe
    @Test
    public void testGetPedidoByIdNotExists() throws Exception {
        URI uri = new URI("/api/pedidos/555");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.NOT_FOUND.value(), status);
    }

    // Crear un nuevo pedido
    @Test
    public void testCreatePedido() throws Exception {
        URI uri = new URI("/api/pedidos");
        String nuevoPedidoJson = """
            {
              "usuario": {
                "id": 1
              },
              "numeroOrden": "ORD-2001",
              "moneda": "PEN",
              "estado": "PENDIENTE",
              "subtotal": 100.00,
              "impuestos": 18.00,
              "total": 118.00
            }
            """;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(nuevoPedidoJson);
        MvcResult result = mockMvc.perform(request).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.CREATED.value(), status);
    }

    // Eliminar pedido inexistente
    @Test
    public void testDeletePedidoNotExists() throws Exception {
        URI uri = new URI("/api/pedidos/7777");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(uri)
                .header("Authorization", "Bearer " + token);
        MvcResult result = mockMvc.perform(request).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.NOT_FOUND.value(), status);
    }
}

