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
public class RestCarritoItemTest {

    @Autowired
    private MockMvc mockMvc;

    // Obtener todos los items del carrito
    @Test
    public void testGetCarritoItems() throws Exception {
        URI uri = new URI("/api/carrito-items");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    // Item con ID 9999 no existe
    @Test
    public void testGetCarritoItemByIdNotExists() throws Exception {
        URI uri = new URI("/api/carrito-items/9999");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.NOT_FOUND.value(), status);
    }

    // Crear un nuevo item en el carrito
    @Test
    public void testCreateCarritoItem() throws Exception {
        URI uri = new URI("/api/carrito-items");
        String nuevoItemJson = """
                            {
                  "id": 1,
                  "carrito": {
                    "id": 1
                  },
                  "varianteId": 2001,
                  "cantidad": 3,
                  "precioUnitario": 59.99,
                  "updatedAt": "2025-09-08T21:30:00"
                }

                            """;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(nuevoItemJson);
        MvcResult result = mockMvc.perform(request).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.CREATED.value(), status);
    }

    // Eliminar item inexistente
    @Test
    public void testDeleteCarritoItemNotExists() throws Exception {
        URI uri = new URI("/api/carrito-items/9999");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(uri);
        MvcResult result = mockMvc.perform(request).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.NOT_FOUND.value(), status);
    }
}
