package com.deliverytech.delivery.dto;

import jakarta.validation.constraints.NotNull;

public record DisponibilidadeDTO(
    @NotNull Boolean disponivel
) {
}
