package dev.projectx.order_manager_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PedidoResponse(
        Long id,
        String clienteNome,
        LocalDate data,
        String status,
        BigDecimal total
) {}