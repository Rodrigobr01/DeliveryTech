package com.deliverytech.delivery.service.impl;

import com.deliverytech.delivery.dto.ProdutoDTO;
import com.deliverytech.delivery.dto.ProdutoResponseDTO;
import com.deliverytech.delivery.entity.Produto;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.exception.BusinessException;
import com.deliverytech.delivery.exception.NotFoundException;
import com.deliverytech.delivery.repository.ProdutoRepository;
import com.deliverytech.delivery.repository.RestauranteRepository;
import com.deliverytech.delivery.service.ProdutoService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final RestauranteRepository restauranteRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository, RestauranteRepository restauranteRepository) {
        this.produtoRepository = produtoRepository;
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public ProdutoResponseDTO cadastrarProduto(ProdutoDTO dto) {
        Restaurante restaurante = restauranteRepository.findById(dto.restauranteId())
            .orElseThrow(() -> new NotFoundException("Restaurante nao encontrado."));

        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setCategoria(dto.categoria());
        produto.setPreco(dto.preco());
        produto.setDescricao(dto.descricao());
        produto.setDisponivel(dto.disponivel() == null ? true : dto.disponivel());
        produto.setRestaurante(restaurante);

        Produto salvo = produtoRepository.save(produto);
        return toResponse(salvo);
    }

    @Override
    public ProdutoResponseDTO buscarProdutoPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Produto nao encontrado."));

        if (!produto.getDisponivel()) {
            throw new BusinessException("Produto indisponivel.");
        }

        return toResponse(produto);
    }

    @Override
    public List<ProdutoResponseDTO> buscarProdutosPorRestaurante(Long restauranteId, Boolean disponivel) {
        List<Produto> produtos = produtoRepository.findByRestauranteId(restauranteId);

        if (Boolean.TRUE.equals(disponivel)) {
            produtos = produtos.stream()
                .filter(Produto::getDisponivel)
                .collect(Collectors.toList());
        }

        return produtos.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    public ProdutoResponseDTO atualizarProduto(Long id, ProdutoDTO dto) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Produto nao encontrado."));

        Restaurante restaurante = restauranteRepository.findById(dto.restauranteId())
            .orElseThrow(() -> new NotFoundException("Restaurante nao encontrado."));

        produto.setNome(dto.nome());
        produto.setCategoria(dto.categoria());
        produto.setPreco(dto.preco());
        produto.setDescricao(dto.descricao());
        produto.setDisponivel(dto.disponivel() == null ? produto.getDisponivel() : dto.disponivel());
        produto.setRestaurante(restaurante);

        Produto salvo = produtoRepository.save(produto);
        return toResponse(salvo);
    }

    @Override
    public ProdutoResponseDTO alterarDisponibilidade(Long id, boolean disponivel) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Produto nao encontrado."));

        produto.setDisponivel(disponivel);
        Produto salvo = produtoRepository.save(produto);
        return toResponse(salvo);
    }

    @Override
    public List<ProdutoResponseDTO> buscarProdutosPorCategoria(String categoria) {
        return produtoRepository.findByCategoria(categoria)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    private ProdutoResponseDTO toResponse(Produto produto) {
        return new ProdutoResponseDTO(
            produto.getId(),
            produto.getNome(),
            produto.getCategoria(),
            produto.getPreco(),
            produto.getDescricao(),
            produto.getDisponivel(),
            produto.getRestaurante() != null ? produto.getRestaurante().getId() : null
        );
    }
}
