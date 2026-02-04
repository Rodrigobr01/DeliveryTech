package com.deliverytech.delivery.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteDTO(
    @NotBlank @Size(min = 2, max = 100) String nome,
    @NotBlank @Email String email,
    @NotBlank @Size(min = 10, max = 11) String telefone,
    @NotBlank @Size(min = 5, max = 200) String endereco
) {
}
