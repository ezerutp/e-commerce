package pe.desarrolloweb.backend.modules.catalago.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.desarrolloweb.backend.modules.catalago.domain.Variante;
import pe.desarrolloweb.backend.modules.catalago.repo.VarianteRepository;

@Service
@RequiredArgsConstructor
public class VarianteService {
    
    private final VarianteRepository varianteRepository;

    public List<Variante> findAll() {
        return varianteRepository.findAll();
    }

    public Optional<Variante> findById(Long id) {
        return varianteRepository.findById(id);
    }

    public Variante save(Variante variante) {
        return varianteRepository.save(variante);
    }

    public void deleteById(Long id) {
        varianteRepository.deleteById(id);
    }
}
