package pe.desarrolloweb.backend.seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import pe.desarrolloweb.backend.entities.CarritoItem;
import pe.desarrolloweb.backend.repositories.CarritoItemRepository;

import java.math.BigDecimal;

@Component
public class DataSeederCarritoItems implements CommandLineRunner {

    private final CarritoItemRepository carritoItemRepository;

    public DataSeederCarritoItems(CarritoItemRepository carritoItemRepository) {
        this.carritoItemRepository = carritoItemRepository;
    }

    @Override
    public void run(String... args) {
        seedCarritoItems();
    }

    private void seedCarritoItems() {
        if (carritoItemRepository.count() == 0) {
            System.out.println("Se crean items de carrito de prueba...");

            CarritoItem item1 = new CarritoItem();
            item1.setCarritoId(1L);
            item1.setVarianteId(101L); 
            item1.setCantidad(2);
            item1.setPrecioUnitario(new BigDecimal("25.50"));
            carritoItemRepository.save(item1);

            CarritoItem item2 = new CarritoItem();
            item2.setCarritoId(1L);
            item2.setVarianteId(102L);
            item2.setCantidad(1);
            item2.setPrecioUnitario(new BigDecimal("40.00"));
            carritoItemRepository.save(item2);
        }
    }
}
