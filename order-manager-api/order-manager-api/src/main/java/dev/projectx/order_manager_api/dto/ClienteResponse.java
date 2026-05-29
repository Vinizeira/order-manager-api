package dev.projectx.order_manager_api.dto;

public record ClienteResponse(
        Long id,
        String nome,
        String email,
        String cpfFormatado,
        String telefoneFormatado
) {}
