package dev.projectx.order_manager_api.repository;

import dev.projectx.order_manager_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {}