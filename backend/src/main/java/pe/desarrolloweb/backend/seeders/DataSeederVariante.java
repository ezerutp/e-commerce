package pe.desarrolloweb.backend.seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import pe.desarrolloweb.backend.entities.Producto;
import pe.desarrolloweb.backend.entities.Variante;
import pe.desarrolloweb.backend.repositories.ProductoRepository;
import pe.desarrolloweb.backend.repositories.VarianteRepository;

@Component
@Order(3) // Se ejecutará después del DataseederProductos
public class DataSeederVariante implements CommandLineRunner {
    
    private final VarianteRepository varianteRepository;
    private final ProductoRepository productoRepository;

    public DataSeederVariante(VarianteRepository varianteRepository, ProductoRepository productoRepository) {
        this.varianteRepository = varianteRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedVariantes();
    }
    
    private void seedVariantes() {
        if (varianteRepository.count() == 0) {
            System.out.println("Creando variantes de productos de prueba...");
            
            // Buscar productos para asociarles variantes
            List<Producto> productos = productoRepository.findAll();
            
            if (!productos.isEmpty()) {
                // Crear variantes para laptop (id 1)
                Optional<Producto> laptopOpt = productos.stream()
                    .filter(p -> p.getNombre().contains("Laptop")).findFirst();
                
                if (laptopOpt.isPresent()) {
                    Producto laptop = laptopOpt.get();
                    
                    // Variante 1: Laptop básica
                    Variante varianteLaptop1 = new Variante();
                    varianteLaptop1.setProducto(laptop);
                    varianteLaptop1.setSku("ASUS-ROG-16GB-512GB");
                    varianteLaptop1.setPrecio(new BigDecimal("1299.99"));
                    varianteLaptop1.setPeso(new BigDecimal("2.30"));
                    varianteLaptop1.setAtributosJson("{\"RAM\":\"16GB\",\"Almacenamiento\":\"512GB SSD\",\"Color\":\"Negro\"}");
                    varianteLaptop1.setActivo(true);
                    
                    // Variante 2: Laptop premium
                    Variante varianteLaptop2 = new Variante();
                    varianteLaptop2.setProducto(laptop);
                    varianteLaptop2.setSku("ASUS-ROG-32GB-1TB");
                    varianteLaptop2.setPrecio(new BigDecimal("1699.99"));
                    varianteLaptop2.setPeso(new BigDecimal("2.32"));
                    varianteLaptop2.setAtributosJson("{\"RAM\":\"32GB\",\"Almacenamiento\":\"1TB SSD\",\"Color\":\"Negro\"}");
                    varianteLaptop2.setActivo(true);
                    
                    varianteRepository.save(varianteLaptop1);
                    varianteRepository.save(varianteLaptop2);
                }
                
                // Crear variantes para smartphone (id 2)
                Optional<Producto> smartphoneOpt = productos.stream()
                    .filter(p -> p.getNombre().contains("iPhone")).findFirst();
                
                if (smartphoneOpt.isPresent()) {
                    Producto smartphone = smartphoneOpt.get();
                    
                    // Variante 1: iPhone 128GB
                    Variante variantePhone1 = new Variante();
                    variantePhone1.setProducto(smartphone);
                    variantePhone1.setSku("IPHN15PM-128-BLK");
                    variantePhone1.setPrecio(new BigDecimal("1099.99"));
                    variantePhone1.setPeso(new BigDecimal("0.24"));
                    variantePhone1.setAtributosJson("{\"Almacenamiento\":\"128GB\",\"Color\":\"Negro\"}");
                    variantePhone1.setActivo(true);
                    
                    // Variante 2: iPhone 256GB
                    Variante variantePhone2 = new Variante();
                    variantePhone2.setProducto(smartphone);
                    variantePhone2.setSku("IPHN15PM-256-BLU");
                    variantePhone2.setPrecio(new BigDecimal("1199.99"));
                    variantePhone2.setPeso(new BigDecimal("0.24"));
                    variantePhone2.setAtributosJson("{\"Almacenamiento\":\"256GB\",\"Color\":\"Azul\"}");
                    variantePhone2.setActivo(true);
                    
                    // Variante 3: iPhone 512GB
                    Variante variantePhone3 = new Variante();
                    variantePhone3.setProducto(smartphone);
                    variantePhone3.setSku("IPHN15PM-512-WHT");
                    variantePhone3.setPrecio(new BigDecimal("1399.99"));
                    variantePhone3.setPeso(new BigDecimal("0.24"));
                    variantePhone3.setAtributosJson("{\"Almacenamiento\":\"512GB\",\"Color\":\"Blanco\"}");
                    variantePhone3.setActivo(true);
                    
                    varianteRepository.save(variantePhone1);
                    varianteRepository.save(variantePhone2);
                    varianteRepository.save(variantePhone3);
                }
                
                // Crear variantes para auriculares (id 3)
                Optional<Producto> auriculareOpt = productos.stream()
                    .filter(p -> p.getNombre().contains("Sony")).findFirst();
                
                if (auriculareOpt.isPresent()) {
                    Producto auricular = auriculareOpt.get();
                    
                    // Variante 1: Auricular Negro
                    Variante varianteAuricular1 = new Variante();
                    varianteAuricular1.setProducto(auricular);
                    varianteAuricular1.setSku("SONY-WH1000XM5-BLK");
                    varianteAuricular1.setPrecio(new BigDecimal("349.99"));
                    varianteAuricular1.setPeso(new BigDecimal("0.35"));
                    varianteAuricular1.setAtributosJson("{\"Color\":\"Negro\",\"Bluetooth\":\"5.2\"}");
                    varianteAuricular1.setActivo(true);
                    
                    // Variante 2: Auricular Plata
                    Variante varianteAuricular2 = new Variante();
                    varianteAuricular2.setProducto(auricular);
                    varianteAuricular2.setSku("SONY-WH1000XM5-SLV");
                    varianteAuricular2.setPrecio(new BigDecimal("349.99"));
                    varianteAuricular2.setPeso(new BigDecimal("0.35"));
                    varianteAuricular2.setAtributosJson("{\"Color\":\"Plata\",\"Bluetooth\":\"5.2\"}");
                    varianteAuricular2.setActivo(true);
                    
                    varianteRepository.save(varianteAuricular1);
                    varianteRepository.save(varianteAuricular2);
                }
                
                System.out.println("Se han creado las variantes de productos exitosamente.");
            } else {
                System.out.println("No se encontraron productos para crear variantes.");
            }
        } else {
            System.out.println("Las variantes ya existen en la base de datos.");
        }
    }
}
