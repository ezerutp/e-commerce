package pe.desarrolloweb.backend.modules.catalago.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.desarrolloweb.backend.modules.catalago.domain.ProductoCategoria;
import pe.desarrolloweb.backend.modules.catalago.repo.ProductoCategoriaRepository;

@Service
@RequiredArgsConstructor
public class ProductoCategoriaService {
    
    private final ProductoCategoriaRepository productoCategoriaRepository;
    
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
