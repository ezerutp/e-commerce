package pe.desarrolloweb.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.desarrolloweb.backend.entities.ProductoCategoria;
import pe.desarrolloweb.backend.repositories.ProductoCategoriaRepository;

@Service
public class ProductoCategoriaService {
    
    @Autowired
    private ProductoCategoriaRepository productoCategoriaRepository;
    
    public List<ProductoCategoria> findAll() {
        return productoCategoriaRepository.findAll();
    }

    public Optional<ProductoCategoria> findById(Long id) {
        return productoCategoriaRepository.findById(id);
    }

    public ProductoCategoria save(ProductoCategoria productoCategoria) {
        return productoCategoriaRepository.save(productoCategoria);
    }

    public void deleteById(Long id) {
        productoCategoriaRepository.deleteById(id);
    }
}
