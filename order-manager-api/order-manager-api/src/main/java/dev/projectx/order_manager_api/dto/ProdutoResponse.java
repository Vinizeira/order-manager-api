package dev.projectx.order_manager_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

public record ProdutoResponse(

        @Schema(description = "ID do produto", example = "1")
        Long id,

        @Schema(description = "Nome do produto", example = "Teclado Mecânico RGB")
        String nome,

        @Schema(description = "Preço do produto", example = "299.90")
        BigDecimal preco,

        @Schema(description = "Quantidade disponível em estoque", example = "15")
        Integer estoque,

        @Schema(description = "Nome da categoria do produto", example = "Periféricos")
        String categoriaNome
) {}
