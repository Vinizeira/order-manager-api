package dev.projectx.order_manager_api.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequest(
        @NotBlank(message = "Nome é obrigatorio")
        String nome
) {}
