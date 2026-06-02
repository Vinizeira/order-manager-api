package dev.projectx.order_manager_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CategoriaRequest(
        @Schema( description = "Nome da categoria", example = "Eletrônicos" )
        @NotBlank(message = "Nome é obrigatorio")
        String nome
) {}
