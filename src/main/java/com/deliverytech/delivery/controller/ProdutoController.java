package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.DisponibilidadeDTO;
import com.deliverytech.delivery.dto.ProdutoDTO;
import com.deliverytech.delivery.dto.ProdutoResponseDTO;
import com.deliverytech.delivery.service.ProdutoService;
import jakarta.validation.Valid;
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
@RequestMapping("/api")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping("/produtos")
    public ResponseEntity<ProdutoResponseDTO> cadastrar(@Valid @RequestBody ProdutoDTO dto) {
        ProdutoResponseDTO criado = produtoService.cadastrarProduto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/produtos/{id}")
    public ProdutoResponseDTO buscarPorId(@PathVariable Long id) {
        return produtoService.buscarProdutoPorId(id);
    }

    @GetMapping("/restaurantes/{restauranteId}/produtos")
    public List<ProdutoResponseDTO> buscarPorRestaurante(
        @PathVariable Long restauranteId,
        @RequestParam(required = false, defaultValue = "true") Boolean disponivel
    ) {
        return produtoService.buscarProdutosPorRestaurante(restauranteId, disponivel);
    }

    @PutMapping("/produtos/{id}")
    public ProdutoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoDTO dto) {
        return produtoService.atualizarProduto(id, dto);
    }

    @PatchMapping("/produtos/{id}/disponibilidade")
    public ProdutoResponseDTO alterarDisponibilidade(
        @PathVariable Long id,
        @Valid @RequestBody DisponibilidadeDTO dto
    ) {
        return produtoService.alterarDisponibilidade(id, dto.disponivel());
    }

    @GetMapping("/produtos/categoria/{categoria}")
    public List<ProdutoResponseDTO> buscarPorCategoria(@PathVariable String categoria) {
        return produtoService.buscarProdutosPorCategoria(categoria);
    }
}
