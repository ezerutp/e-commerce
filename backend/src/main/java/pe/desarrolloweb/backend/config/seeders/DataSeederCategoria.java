package pe.desarrolloweb.backend.config.seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

import pe.desarrolloweb.backend.modules.catalago.domain.Categoria;
import pe.desarrolloweb.backend.modules.catalago.repo.CategoriaRepository;

@Component
@Order(4)
public class DataSeederCategoria implements CommandLineRunner {
    
    private final CategoriaRepository categoriaRepository;

    public DataSeederCategoria(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedCategorias();
    }
    
    private void seedCategorias() {
        if (categoriaRepository.count() == 0) {
            System.out.println("Creando categorías de prueba...");
            
            // Lista de categorías a crear
            List<Categoria> categorias = Arrays.asList(
                createCategoria("Electrónicos", "Productos electrónicos y gadgets tecnológicos"),
                createCategoria("Computadoras", "Laptops, desktops y accesorios para computadoras"),
                createCategoria("Smartphones", "Teléfonos móviles y accesorios"),
                createCategoria("Audio", "Auriculares, altavoces y equipos de sonido"),
                createCategoria("Videojuegos", "Consolas, juegos y accesorios para gaming"),
                createCategoria("Fotografía", "Cámaras, lentes y accesorios fotográficos"),
                createCategoria("Hogar Inteligente", "Dispositivos para automatización del hogar"),
                createCategoria("Accesorios", "Accesorios diversos para dispositivos electrónicos")
            );
            
            // Guardar todas las categorías
            categoriaRepository.saveAll(categorias);
            
            System.out.println("Se han creado " + categorias.size() + " categorías exitosamente.");
        } else {
            System.out.println("Las categorías ya existen en la base de datos.");
        }
    }
    
    // Método auxiliar para crear categoría
    private Categoria createCategoria(String nombre, String descripcion) {
        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        return categoria;
    }
}
