package pe.desarrolloweb.backend.modules.pedidos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.desarrolloweb.backend.modules.pedidos.domain.PedidoItem;
import pe.desarrolloweb.backend.modules.pedidos.repo.PedidoItemRepository;

@Service
@RequiredArgsConstructor
public class PedidoItemService {

    private final PedidoItemRepository pedidoItemRepository;

    public List<PedidoItem> findAll() {
        return pedidoItemRepository.findAll();
    }

    public Optional<PedidoItem> findById(Long id) {
        return pedidoItemRepository.findById(id);
    }

    public PedidoItem save(PedidoItem pedidoItem) {
        return pedidoItemRepository.save(pedidoItem);
    }

    public void deleteById(Long id) {
        pedidoItemRepository.deleteById(id);
    }
}