package dev.projectx.order_manager_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ClienteResponse(

        @Schema(description = "ID do cliente", example = "1")
        Long id,

        @Schema(description = "Nome do cliente", example = "Lucas Silva")
        String nome,

        @Schema(description = "Email do cliente", example = "lucas@gmail.com")
        String email,

        @Schema(description = "CPF formatado do cliente", example = "123.456.789-00")
        String cpfFormatado,

        @Schema(description = "Telefone formatado do cliente", example = "(11) 99999-9999")
        String telefoneFormatado
) {}

