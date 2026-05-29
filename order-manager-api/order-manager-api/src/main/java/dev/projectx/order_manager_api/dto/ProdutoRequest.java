package dev.projectx.order_manager_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProdutoRequest(
        @NotBlank(message = "Nome é Obrigatório")
        String nome,

        @NotNull(message = "Preço é Obrigatório")
        @Positive(message = "Preço deve ser maior que zero")
        BigDecimal preco,

        @NotNull(message = "Estoque é obrigatorio")
        @Positive(message = "Estoque tem que ser maior que zero")
        Integer estoque,

        @NotNull(message = "Categoria é obrigatoria")
        Long categoriaId
) {}
