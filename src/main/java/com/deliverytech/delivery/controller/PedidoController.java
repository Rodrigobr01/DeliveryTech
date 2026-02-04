package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.AtualizarStatusDTO;
import com.deliverytech.delivery.dto.PedidoCalculoDTO;
import com.deliverytech.delivery.dto.PedidoDTO;
import com.deliverytech.delivery.dto.PedidoResponseDTO;
import com.deliverytech.delivery.dto.PedidoResumoDTO;
import com.deliverytech.delivery.service.PedidoService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/pedidos")
    public ResponseEntity<PedidoResponseDTO> criarPedido(@Valid @RequestBody PedidoDTO dto) {
        PedidoResponseDTO criado = pedidoService.criarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/pedidos/{id}")
    public PedidoResponseDTO buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPedidoPorId(id);
    }

    @GetMapping("/clientes/{clienteId}/pedidos")
    public List<PedidoResumoDTO> buscarPorCliente(@PathVariable Long clienteId) {
        return pedidoService.buscarPedidosPorCliente(clienteId);
    }

    @PatchMapping("/pedidos/{id}/status")
    public PedidoResponseDTO atualizarStatus(
        @PathVariable Long id,
        @Valid @RequestBody AtualizarStatusDTO dto
    ) {
        return pedidoService.atualizarStatusPedido(id, dto.status());
    }

    @DeleteMapping("/pedidos/{id}")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/pedidos/calcular")
    public Map<String, BigDecimal> calcularTotal(@Valid @RequestBody PedidoCalculoDTO dto) {
        BigDecimal total = pedidoService.calcularTotalPedido(dto.restauranteId(), dto.itens());
        return Map.of("total", total);
    }
}
