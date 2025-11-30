package pe.desarrolloweb.backend.modules.pagos.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import pe.desarrolloweb.backend.modules.pagos.domain.Pago;
import pe.desarrolloweb.backend.modules.pagos.mapper.PagoMapper;
import pe.desarrolloweb.backend.modules.pagos.service.PagoService;
import pe.desarrolloweb.backend.modules.pagos.web.dto.PagoRequest;
import pe.desarrolloweb.backend.modules.pagos.web.dto.PagoResponse;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public List<PagoResponse> getAllPagos() {
        return pagoService.findAll().stream()
                .map(PagoMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponse> getPagoById(@PathVariable("id") Long id) {
        Optional<Pago> pago = pagoService.findById(id);
        if (pago.isPresent()) {
            PagoResponse response = PagoMapper.toResponse(pago.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PagoResponse> createPago(@Valid @RequestBody PagoRequest request) {
        Pago pago = PagoMapper.toEntity(request);
        Pago nuevo = pagoService.save(pago);
        PagoResponse response = PagoMapper.toResponse(nuevo);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PagoResponse> updatePago(@PathVariable("id") Long id, @Valid @RequestBody PagoRequest request) {
        Optional<Pago> existente = pagoService.findById(id);
        if (existente.isPresent()) {
            Pago pago = existente.get();
            if (request.pedidoId() != null) {
                pago.getPedido().setId(request.pedidoId());
            }
            if (request.metodo() != null) {
                pago.setMetodo(request.metodo());
            }
            if (request.proveedor() != null) {
                pago.setProveedor(request.proveedor());
            }
            if (request.monto() != null) {
                pago.setMonto(request.monto());
            }
            if (request.moneda() != null) {
                pago.setMoneda(request.moneda());
            }
            if (request.estado() != null) {
                pago.setEstado(request.estado());
            }
            if (request.referenciaProveedor() != null) {
                pago.setReferenciaProveedor(request.referenciaProveedor());
            }
            if (request.autorizadoEn() != null) {
                pago.setAutorizadoEn(request.autorizadoEn());
            }
            if (request.capturadoEn() != null) {
                pago.setCapturadoEn(request.capturadoEn());
            }

            Pago actualizado = pagoService.save(pago);
            PagoResponse response = PagoMapper.toResponse(actualizado);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePago(@PathVariable("id") Long id) {
        if (pagoService.findById(id).isPresent()) {
            pagoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
