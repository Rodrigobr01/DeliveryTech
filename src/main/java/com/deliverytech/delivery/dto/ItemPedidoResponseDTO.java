package com.deliverytech.delivery.dto;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
    Long produtoId,
    String produtoNome,
    Integer quantidade,
    BigDecimal precoUnitario,
    BigDecimal subtotal
) {
}
