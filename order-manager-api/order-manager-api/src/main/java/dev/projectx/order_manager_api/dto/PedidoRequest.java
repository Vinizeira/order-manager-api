package dev.projectx.order_manager_api.dto;

import dev.projectx.order_manager_api.model.ItemPedido;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PedidoRequest(
        @NotNull(message = "Cliente é obrigatório")
        Long clienteId,

        @NotNull(message = "Itens são obrigatórios")
        @Size(min = 1, message = "Pedido deve ter pelo menos um item")
        List<ItemPedidoRequest> itens
) {}
