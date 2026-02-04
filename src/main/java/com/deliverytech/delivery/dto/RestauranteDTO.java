package com.deliverytech.delivery.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record RestauranteDTO(
    @NotBlank @Size(min = 2, max = 120) String nome,
    @NotBlank @Size(min = 2, max = 60) String categoria,
    @NotBlank @Size(min = 10, max = 11) String telefone,
    @NotNull @DecimalMin("0.00") BigDecimal taxaEntrega,
    @NotNull @Min(10) @Max(120) Integer tempoEntregaMin
) {
}
