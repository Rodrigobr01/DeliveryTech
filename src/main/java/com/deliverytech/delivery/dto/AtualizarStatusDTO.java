package com.deliverytech.delivery.dto;

import com.deliverytech.delivery.entity.StatusPedido;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusDTO(
    @NotNull StatusPedido status
) {
}
