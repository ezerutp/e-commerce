package pe.desarrolloweb.backend.modules.carrito.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.desarrolloweb.backend.modules.carrito.domain.CarritoItem;
import pe.desarrolloweb.backend.modules.carrito.repo.CarritoItemRepository;

@Service
@RequiredArgsConstructor
public class CarritoItemService {

    private final CarritoItemRepository carritoItemRepository;

    public List<CarritoItem> findAll() {
        return carritoItemRepository.findAll();
    }

    public Optional<CarritoItem> findById(Long id) {
        return carritoItemRepository.findById(id);
    }

    public CarritoItem save(CarritoItem carritoItem) {
        return carritoItemRepository.save(carritoItem);
    }

    public void deleteById(Long id) {
        carritoItemRepository.deleteById(id);
    }
}
