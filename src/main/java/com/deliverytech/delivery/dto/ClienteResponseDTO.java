package com.deliverytech.delivery.dto;

import java.time.LocalDateTime;

public record ClienteResponseDTO(
    Long id,
    String nome,
    String email,
    String telefone,
    String endereco,
    Boolean ativo,
    LocalDateTime dataCadastro
) {
}
