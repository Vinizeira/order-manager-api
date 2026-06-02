
package dev.projectx.order_manager_api.controller;

import dev.projectx.order_manager_api.dto.ProdutoRequest;
import dev.projectx.order_manager_api.dto.ProdutoResponse;
import dev.projectx.order_manager_api.service.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")

@Tag(
        name = "Produtos",
        description = "Endpoints para gerenciamento de produtos"
)

public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping

    @Operation(
            summary = "Listar produtos",
            description = "Retorna todos os produtos cadastrados"
    )

    public List<ProdutoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")

    @Operation(
            summary = "Buscar produto por ID",
            description = "Retorna um produto específico pelo ID informado"
    )

    public ResponseEntity<ProdutoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping

    @Operation(
            summary = "Cadastrar produto",
            description = "Cria um novo produto no sistema"
    )

    public ResponseEntity<ProdutoResponse> salvar(
            @RequestBody @Valid ProdutoRequest produto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.salvar(produto));
    }

    @DeleteMapping("/{id}")

    @Operation(
            summary = "Deletar produto",
            description = "Remove um produto pelo ID informado"
    )

    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{categoriaId}")

    @Operation(
            summary = "Buscar produtos por categoria",
            description = "Retorna todos os produtos de uma categoria"
    )

    public List<ProdutoResponse> buscarPorCategoria(
            @PathVariable Long categoriaId) {

        return service.buscarPorCategoria(categoriaId);
    }

    @GetMapping("/estoque-baixo")

    @Operation(
            summary = "Buscar produtos com estoque baixo",
            description = "Retorna produtos com quantidade abaixo do limite informado"
    )

    public List<ProdutoResponse> buscarEstoqueBaixo(
            @RequestParam Integer quantidade) {

        return service.buscarEstoqueBaixo(quantidade);
    }
}

