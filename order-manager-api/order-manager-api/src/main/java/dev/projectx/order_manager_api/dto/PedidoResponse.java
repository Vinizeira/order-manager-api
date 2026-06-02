package dev.projectx.order_manager_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PedidoResponse(

        @Schema(description = "ID do pedido", example = "1")
        Long id,

        @Schema(description = "Nome do cliente", example = "Lucas Silva")
        String clienteNome,

        @Schema(description = "Data do pedido", example = "2025-06-01")
        LocalDate data,

        @Schema(description = "Status do pedido", example = "PENDENTE")
        String status,

        @Schema(description = "Valor total do pedido", example = "299.90")
        BigDecimal total
) {}