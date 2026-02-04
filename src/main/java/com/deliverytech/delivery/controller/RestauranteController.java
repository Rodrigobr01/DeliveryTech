package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.RestauranteDTO;
import com.deliverytech.delivery.dto.RestauranteResponseDTO;
import com.deliverytech.delivery.service.RestauranteService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @PostMapping
    public ResponseEntity<RestauranteResponseDTO> cadastrar(@Valid @RequestBody RestauranteDTO dto) {
        RestauranteResponseDTO criado = restauranteService.cadastrarRestaurante(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/{id}")
    public RestauranteResponseDTO buscarPorId(@PathVariable Long id) {
        return restauranteService.buscarRestaurantePorId(id);
    }

    @GetMapping
    public List<RestauranteResponseDTO> listar(@RequestParam(required = false) String categoria) {
        if (categoria != null && !categoria.isBlank()) {
            return restauranteService.buscarRestaurantesPorCategoria(categoria);
        }
        return restauranteService.buscarRestaurantesDisponiveis();
    }

    @GetMapping("/categoria/{categoria}")
    public List<RestauranteResponseDTO> buscarPorCategoria(@PathVariable String categoria) {
        return restauranteService.buscarRestaurantesPorCategoria(categoria);
    }

    @PutMapping("/{id}")
    public RestauranteResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody RestauranteDTO dto) {
        return restauranteService.atualizarRestaurante(id, dto);
    }

    @PatchMapping("/{id}/status")
    public RestauranteResponseDTO alterarStatus(@PathVariable Long id) {
        return restauranteService.alterarStatus(id);
    }

    @GetMapping("/{id}/taxa-entrega/{cep}")
    public BigDecimal calcularTaxaEntrega(@PathVariable Long id, @PathVariable String cep) {
        return restauranteService.calcularTaxaEntrega(id, cep);
    }
}
