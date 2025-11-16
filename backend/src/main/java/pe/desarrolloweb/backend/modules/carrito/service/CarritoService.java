package pe.desarrolloweb.backend.modules.carrito.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.desarrolloweb.backend.modules.carrito.domain.Carrito;
import pe.desarrolloweb.backend.modules.carrito.repo.CarritoRepository;

@Service
@RequiredArgsConstructor
public class CarritoService {
    
    private final CarritoRepository carritoRepository;

    public List<Carrito> findAll() {
        return carritoRepository.findAll();
    }

    public Optional<Carrito> findById(Long id) {
        return carritoRepository.findById(id);
    }

    public Carrito save(Carrito carrito) {
         if (carrito == null) return null;
        return carritoRepository.save(carrito);
    }

    public void deleteById(Long id) {
        if (id == null) return;
        carritoRepository.deleteById(id);
    }
}
