package dev.projectx.order_manager_api.repository;

import dev.projectx.order_manager_api.model.Pedido;
import dev.projectx.order_manager_api.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    List<Pedido> findByStatus(StatusPedido status);
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByDataBetween(LocalDate inicio, LocalDate fim);
}
