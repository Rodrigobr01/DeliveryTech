package com.deliverytech.delivery.dto;

import com.deliverytech.delivery.entity.StatusPedido;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
    Long id,
    Long clienteId,
    Long restauranteId,
    String enderecoEntrega,
    StatusPedido status,
    LocalDateTime dataPedido,
    BigDecimal taxaEntrega,
    BigDecimal total,
    List<ItemPedidoResponseDTO> itens
) {
}
