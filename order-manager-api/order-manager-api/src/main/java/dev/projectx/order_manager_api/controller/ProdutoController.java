package dev.projectx.order_manager_api.controller;

import dev.projectx.order_manager_api.dto.ProdutoRequest;
import dev.projectx.order_manager_api.dto.ProdutoResponse;
import dev.projectx.order_manager_api.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProdutoResponse> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscar(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> salvar(@RequestBody @Valid ProdutoRequest produto){
        return ResponseEntity.ok(service.salvar(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<ProdutoResponse> buscarPorCategoria(@PathVariable Long categoriaId) {
        return service.buscarPorCategoria(categoriaId);
    }

    @GetMapping("/estoque-baixo")
    public List<ProdutoResponse> buscarEstoqueBaixo(@RequestParam Integer quantidade) {
        return service.buscarEstoqueBaixo(quantidade);
    }
}
