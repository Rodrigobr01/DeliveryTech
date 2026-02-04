package com.deliverytech.delivery.service.impl;

import com.deliverytech.delivery.dto.RestauranteDTO;
import com.deliverytech.delivery.dto.RestauranteResponseDTO;
import com.deliverytech.delivery.entity.Restaurante;
import com.deliverytech.delivery.exception.NotFoundException;
import com.deliverytech.delivery.repository.RestauranteRepository;
import com.deliverytech.delivery.service.RestauranteService;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class RestauranteServiceImpl implements RestauranteService {

    private final RestauranteRepository restauranteRepository;

    public RestauranteServiceImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public RestauranteResponseDTO cadastrarRestaurante(RestauranteDTO dto) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(dto.nome());
        restaurante.setCategoria(dto.categoria());
        restaurante.setTelefone(dto.telefone());
        restaurante.setTaxaEntrega(dto.taxaEntrega());
        restaurante.setTempoEntregaMin(dto.tempoEntregaMin());
        restaurante.setAtivo(true);

        Restaurante salvo = restauranteRepository.save(restaurante);
        return toResponse(salvo);
    }

    @Override
    public RestauranteResponseDTO buscarRestaurantePorId(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Restaurante nao encontrado."));
        return toResponse(restaurante);
    }

    @Override
    public List<RestauranteResponseDTO> buscarRestaurantesPorCategoria(String categoria) {
        return restauranteRepository.findByCategoria(categoria)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<RestauranteResponseDTO> buscarRestaurantesDisponiveis() {
        return restauranteRepository.findByAtivoTrue()
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    public RestauranteResponseDTO atualizarRestaurante(Long id, RestauranteDTO dto) {
        Restaurante restaurante = restauranteRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Restaurante nao encontrado."));

        restaurante.setNome(dto.nome());
        restaurante.setCategoria(dto.categoria());
        restaurante.setTelefone(dto.telefone());
        restaurante.setTaxaEntrega(dto.taxaEntrega());
        restaurante.setTempoEntregaMin(dto.tempoEntregaMin());

        Restaurante salvo = restauranteRepository.save(restaurante);
        return toResponse(salvo);
    }

    @Override
    public BigDecimal calcularTaxaEntrega(Long restauranteId, String cep) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
            .orElseThrow(() -> new NotFoundException("Restaurante nao encontrado."));
        return restaurante.getTaxaEntrega();
    }

    @Override
    public RestauranteResponseDTO alterarStatus(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Restaurante nao encontrado."));

        restaurante.setAtivo(!restaurante.getAtivo());
        Restaurante salvo = restauranteRepository.save(restaurante);
        return toResponse(salvo);
    }

    private RestauranteResponseDTO toResponse(Restaurante restaurante) {
        return new RestauranteResponseDTO(
            restaurante.getId(),
            restaurante.getNome(),
            restaurante.getCategoria(),
            restaurante.getTelefone(),
            restaurante.getTaxaEntrega(),
            restaurante.getTempoEntregaMin(),
            restaurante.getAvaliacao(),
            restaurante.getAtivo()
        );
    }
}
