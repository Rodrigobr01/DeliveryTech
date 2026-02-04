package com.deliverytech.delivery.dto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
    Long id,
    String nome,
    String categoria,
    BigDecimal preco,
    String descricao,
    Boolean disponivel,
    Long restauranteId
) {
}
