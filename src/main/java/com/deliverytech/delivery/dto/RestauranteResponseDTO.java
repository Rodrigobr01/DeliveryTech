package com.deliverytech.delivery.dto;

import java.math.BigDecimal;

public record RestauranteResponseDTO(
    Long id,
    String nome,
    String categoria,
    String telefone,
    BigDecimal taxaEntrega,
    Integer tempoEntregaMin,
    Double avaliacao,
    Boolean ativo
) {
}
