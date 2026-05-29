package dev.projectx.order_manager_api.repository;

import dev.projectx.order_manager_api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {}
