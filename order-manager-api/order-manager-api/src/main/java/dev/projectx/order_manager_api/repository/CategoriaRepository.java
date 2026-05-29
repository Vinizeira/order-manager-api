package dev.projectx.order_manager_api.repository;

import dev.projectx.order_manager_api.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {}
