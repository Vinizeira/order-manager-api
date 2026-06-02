package dev.projectx.order_manager_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CategoriaResponse(
        @Schema( description = "ID da categoria", example = "1" )
        Long id,
        @Schema(description = "Nome da categoria", example = "Eletrônicos")
        String nome
){}
