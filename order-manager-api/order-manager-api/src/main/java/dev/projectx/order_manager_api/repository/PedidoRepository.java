package dev.projectx.order_manager_api.repository;

import dev.projectx.order_manager_api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {}
