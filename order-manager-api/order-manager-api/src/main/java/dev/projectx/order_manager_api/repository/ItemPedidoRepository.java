package dev.projectx.order_manager_api.repository;

import dev.projectx.order_manager_api.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {}
