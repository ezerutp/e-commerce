package pe.desarrolloweb.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.desarrolloweb.backend.entities.Categoria;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaService categoriaService;

    public List<Categoria> findAll() {
        return categoriaService.findAll();
    }

    public Optional<Categoria> findById(Long id) {
        return categoriaService.findById(id);
    }

    public Categoria save(Categoria categoria) {
        return categoriaService.save(categoria);
    }

    public void deleteById(Long id) {
        categoriaService.deleteById(id);
    }
}
