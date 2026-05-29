package dev.projectx.order_manager_api.dto;

import java.math.BigDecimal;

public record ProdutoResponse(
        Long id,
        String nome,
        BigDecimal preco,
        Integer estoque,
        String categoriaNome
) {}
