package dev.projectx.order_manager_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemPedidoRequest(
        @Schema(description = "Id do Produto", example = "1")
        @NotNull(message =  "Produto é obrigatório")
        Long produtoId,

        @Schema(description = "Quantidade do Produto", example = "3")
        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "Quantidade deve ser maior que zero")
        Integer quantidade
) {}
