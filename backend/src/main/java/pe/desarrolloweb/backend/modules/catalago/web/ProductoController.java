package pe.desarrolloweb.backend.modules.catalago.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pe.desarrolloweb.backend.modules.catalago.domain.Producto;
import pe.desarrolloweb.backend.modules.catalago.mapper.ProductoMapper;
import pe.desarrolloweb.backend.modules.catalago.service.ProductoService;
import pe.desarrolloweb.backend.modules.catalago.web.dto.ProductoRequest;
import pe.desarrolloweb.backend.modules.catalago.web.dto.ProductoResponse;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    // Obtener todos los productos
    @GetMapping
    public List<ProductoResponse> getAllProductos() {
        return productoService.findAll().stream()
                .map(ProductoMapper::toResponse)
                .toList();
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> getProductoById(@PathVariable("id") Long id) {
        Optional<Producto> producto = productoService.findById(id);
        if (producto.isPresent()) {
            ProductoResponse response = ProductoMapper.toResponse(producto.get());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un nuevo producto
    @PostMapping
    public ResponseEntity<ProductoResponse> createProducto(@Valid @RequestBody ProductoRequest request) {
        Producto producto = ProductoMapper.toEntity(request);
        Producto nuevoProducto = productoService.save(producto);
        ProductoResponse response = ProductoMapper.toResponse(nuevoProducto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Actualizar un producto existente
    @PatchMapping("/{id}")
    public ResponseEntity<ProductoResponse> updateProducto(@PathVariable("id") Long id, @Valid @RequestBody ProductoRequest request) {
        Optional<Producto> productoExistente = productoService.findById(id);
        if (productoExistente.isPresent()) {
            Producto producto = productoExistente.get();
            if (request.nombre() != null) {
                producto.setNombre(request.nombre());
            }
            if (request.descripcion() != null) {
                producto.setDescripcion(request.descripcion());
            }
            if (request.marca() != null) {
                producto.setMarca(request.marca());
            }
            if (request.activo() != null) {
                producto.setActivo(request.activo());
            }
            if (request.slug() != null) {
                producto.setSlug(request.slug());
            }
            Producto productoActualizado = productoService.save(producto);
            ProductoResponse response = ProductoMapper.toResponse(productoActualizado);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable("id") Long id) {
        Optional<Producto> producto = productoService.findById(id);
        if (producto.isPresent()) {
            productoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
