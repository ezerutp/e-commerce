package pe.desarrolloweb.backend.config.seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import pe.desarrolloweb.backend.modules.catalago.domain.Producto;
import pe.desarrolloweb.backend.modules.catalago.repo.ProductoRepository;

@Component
@Order(2)
public class DataseederProductos implements CommandLineRunner {

    private final ProductoRepository productoRepository;

    public DataseederProductos(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedProductos();
    }
    
    private void seedProductos() {
        if (productoRepository.count() == 0) {
            System.out.println("Se crean productos de prueba...");
            
            // Producto 1: Laptop Gaming
            Producto laptop = new Producto();
            laptop.setNombre("Laptop Gaming ASUS ROG Strix G15");
            laptop.setDescripcion("Laptop gaming de alto rendimiento con procesador AMD Ryzen 7, 16GB RAM, tarjeta gráfica NVIDIA GeForce RTX 3060, SSD 512GB. Perfecta para gaming y diseño profesional.");
            laptop.setMarca("ASUS");
            laptop.setActivo(true);
            laptop.setSlug("laptop-gaming-asus-rog-strix-g15");
            
            // Producto 2: Smartphone
            Producto smartphone = new Producto();
            smartphone.setNombre("iPhone 15 Pro Max");
            smartphone.setDescripcion("Smartphone premium con chip A17 Pro, cámara de 48MP con zoom óptico 5x, pantalla Super Retina XDR de 6.7 pulgadas, 256GB de almacenamiento. Resistente al agua y polvo.");
            smartphone.setMarca("Apple");
            smartphone.setActivo(true);
            smartphone.setSlug("iphone-15-pro-max");
            
            // Producto 3: Auriculares Inalámbricos
            Producto auriculares = new Producto();
            auriculares.setNombre("Sony WH-1000XM5 Auriculares Bluetooth");
            auriculares.setDescripcion("Auriculares inalámbricos con cancelación de ruido líder en la industria, 30 horas de batería, audio de alta resolución y micrófono optimizado para llamadas cristalinas.");
            auriculares.setMarca("Sony");
            auriculares.setActivo(true);
            auriculares.setSlug("sony-wh-1000xm5-auriculares-bluetooth");
            
            // Producto 4: Monitor 4K
            Producto monitor = new Producto();
            monitor.setNombre("Monitor LG UltraWide 34WN80C-B 34\"");
            monitor.setDescripcion("Monitor ultrawide de 34 pulgadas con resolución QHD (3440x1440), tecnología IPS, USB-C con 60W de carga, HDR10 y FreeSync. Ideal para productividad y entretenimiento.");
            monitor.setMarca("LG");
            monitor.setActivo(true);
            monitor.setSlug("monitor-lg-ultrawide-34wn80c-b-34");
            
            // Producto 5: Teclado Mecánico
            Producto teclado = new Producto();
            teclado.setNombre("Corsair K95 RGB Platinum XT");
            teclado.setDescripcion("Teclado mecánico gaming con switches Cherry MX Speed, iluminación RGB personalizable, 6 teclas macro dedicadas y reposamuñecas extraíble. Construcción de aluminio premium.");
            teclado.setMarca("Corsair");
            teclado.setActivo(true);
            teclado.setSlug("corsair-k95-rgb-platinum-xt");
            
            // Guardar productos
            productoRepository.save(laptop);
            productoRepository.save(smartphone);
            productoRepository.save(auriculares);
            productoRepository.save(monitor);
            productoRepository.save(teclado);
            
            System.out.println("Se han creado 5 productos de prueba exitosamente.");
        } else {
            System.out.println("Los productos ya existen en la base de datos.");
        }
    }

}
