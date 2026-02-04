package com.deliverytech.delivery.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProdutoDTO(
    @NotNull Long restauranteId,
    @NotBlank @Size(min = 2, max = 80) String nome,
    @NotBlank @Size(min = 2, max = 60) String categoria,
    @NotNull @DecimalMin("0.01") @DecimalMax("500.00") BigDecimal preco,
    @NotBlank @Size(min = 10, max = 200) String descricao,
    Boolean disponivel
) {
}
