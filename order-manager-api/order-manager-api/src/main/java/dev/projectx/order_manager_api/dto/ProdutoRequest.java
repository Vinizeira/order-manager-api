package dev.projectx.order_manager_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProdutoRequest(

        @Schema(description = "Nome do produto", example = "Teclado Mecânico RGB")
        @NotBlank(message = "Nome é Obrigatório")
        String nome,

        @Schema(description = "Preço do produto", example = "299.90")
        @NotNull(message = "Preço é Obrigatório")
        @Positive(message = "Preço deve ser maior que zero")
        BigDecimal preco,

        @Schema(description = "Quantidade disponível em estoque", example = "15")
        @NotNull(message = "Estoque é obrigatorio")
        @Positive(message = "Estoque tem que ser maior que zero")
        Integer estoque,

        @Schema(description = "ID da categoria do produto", example = "1")
        @NotNull(message = "Categoria é obrigatoria")
        Long categoriaId
) {}
