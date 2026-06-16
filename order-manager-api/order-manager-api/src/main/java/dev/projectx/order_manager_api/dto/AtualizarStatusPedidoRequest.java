package dev.projectx.order_manager_api.dto;

import dev.projectx.order_manager_api.model.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusPedidoRequest(
        @Schema(description = "Novo status do pedido", example = "PAGO")
        @NotNull(message = "Status e obrigatorio")
        StatusPedido status
) {}
