package dev.projectx.order_manager_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PedidoRequest(
        @Schema(description = "Id do Cliente", example = "1")
        @NotNull(message = "Cliente é obrigatório")
        Long clienteId,

        @Schema(description = "Número de Itens", example = "2")
        @NotNull(message = "Itens são obrigatórios")
        @Size(min = 1, message = "Pedido deve ter pelo menos um item")
        List<ItemPedidoRequest> itens
) {}
