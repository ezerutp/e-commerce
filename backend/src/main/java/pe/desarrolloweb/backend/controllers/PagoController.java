package pe.desarrolloweb.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.desarrolloweb.backend.entities.Pago;
import pe.desarrolloweb.backend.services.PagoService;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public List<Pago> getAllPagos() {
        return pagoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> getPagoById(@PathVariable Long id) {
        Optional<Pago> pago = pagoService.findById(id);
        return pago.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pago> createPago(@RequestBody Pago pago) {
        Pago nuevo = pagoService.save(pago);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Pago> updatePago(@PathVariable Long id, @RequestBody Pago detalles) {
        Optional<Pago> existente = pagoService.findById(id);
        if (existente.isPresent()) {
            Pago pago = existente.get();
            if (detalles.getPedido() != null) pago.setPedido(detalles.getPedido());
            if (detalles.getMetodo() != null) pago.setMetodo(detalles.getMetodo());
            if (detalles.getProveedor() != null) pago.setProveedor(detalles.getProveedor());
            if (detalles.getMonto() != null) pago.setMonto(detalles.getMonto());
            if (detalles.getMoneda() != null) pago.setMoneda(detalles.getMoneda());
            if (detalles.getEstado() != null) pago.setEstado(detalles.getEstado());
            if (detalles.getReferenciaProveedor() != null) pago.setReferenciaProveedor(detalles.getReferenciaProveedor());
            if (detalles.getAutorizadoEn() != null) pago.setAutorizadoEn(detalles.getAutorizadoEn());
            if (detalles.getCapturadoEn() != null) pago.setCapturadoEn(detalles.getCapturadoEn());

            Pago actualizado = pagoService.save(pago);
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        if (pagoService.findById(id).isPresent()) {
            pagoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
