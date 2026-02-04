package com.deliverytech.delivery.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PedidoCalculoDTO(
    @NotNull Long restauranteId,
    @NotEmpty List<@Valid ItemPedidoDTO> itens
) {
}
