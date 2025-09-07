package pe.desarrolloweb.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.desarrolloweb.backend.entities.CarritoItem;
import pe.desarrolloweb.backend.repositories.CarritoItemRepository;

@Service
public class CarritoItemService {

    @Autowired
    private CarritoItemRepository carritoItemRepository;

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
