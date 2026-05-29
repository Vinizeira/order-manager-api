package dev.projectx.order_manager_api.repository;

import dev.projectx.order_manager_api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByCategoriaId(Long categoriaId);
    List<Produto> findByEstoqueLessThan(Integer quantidade);
}