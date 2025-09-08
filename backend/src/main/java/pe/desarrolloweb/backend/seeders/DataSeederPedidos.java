package pe.desarrolloweb.backend.seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import pe.desarrolloweb.backend.entities.Pedido;
import pe.desarrolloweb.backend.enums.EstadoPedido;
import pe.desarrolloweb.backend.repositories.PedidoRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataSeederPedidos implements CommandLineRunner {

    private final PedidoRepository pedidoRepository;

    public DataSeederPedidos(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void run(String... args) {
        seedPedidos();
    }

    private void seedPedidos() {
        if (pedidoRepository.count() == 0) {
            System.out.println("Se crean pedidos de prueba...");

             // Usuario 1
            Pedido pedido1 = new Pedido();
            pedido1.setClienteId(1L); 
            pedido1.setNumeroOrden("ORD-1001");
            pedido1.setMoneda("PEN");
            pedido1.setEstado(EstadoPedido.PENDIENTE);
            pedido1.setSubtotal(new BigDecimal("100.00"));
            pedido1.setImpuestos(new BigDecimal("18.00"));
            pedido1.setTotal(new BigDecimal("118.00"));
            pedido1.setCreatedAt(LocalDateTime.now());
            pedidoRepository.save(pedido1);

            // Usuario 2
            Pedido pedido2 = new Pedido();
            pedido2.setClienteId(2L);
            pedido2.setNumeroOrden("ORD-1002");
            pedido2.setMoneda("USD");
            pedido2.setEstado(EstadoPedido.COMPLETADO);
            pedido2.setSubtotal(new BigDecimal("50.00"));
            pedido2.setImpuestos(new BigDecimal("9.00"));
            pedido2.setTotal(new BigDecimal("59.00"));
            pedido2.setCreatedAt(LocalDateTime.now());
            pedidoRepository.save(pedido2);
        }
    }
}
