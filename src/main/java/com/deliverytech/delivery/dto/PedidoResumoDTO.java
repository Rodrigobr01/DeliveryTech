package com.deliverytech.delivery.dto;

import com.deliverytech.delivery.entity.StatusPedido;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoResumoDTO(
    Long id,
    StatusPedido status,
    LocalDateTime dataPedido,
    BigDecimal total
) {
}
