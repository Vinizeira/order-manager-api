package dev.projectx.order_manager_api.controller;

import dev.projectx.order_manager_api.dto.CategoriaRequest;
import dev.projectx.order_manager_api.dto.CategoriaResponse;
import dev.projectx.order_manager_api.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@Tag( name = "Categorias", description = "Endpoints para gerenciamento de categorias" )
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation( summary = "Listar categorias", description = "Retorna todas as categorias cadastradas" )
    public List<CategoriaResponse> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation( summary = "Buscar categoria por ID", description = "Retorna uma categoria específica pelo ID informado" )
    public ResponseEntity<CategoriaResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation( summary = "Cadastrar categoria", description = "Cria uma nova categoria no sistema" )
    public ResponseEntity<CategoriaResponse> salvar(@RequestBody @Valid CategoriaRequest request) {
        return ResponseEntity.ok(service.salvar(request));
    }

    @DeleteMapping("/{id}")
    @Operation( summary = "Deletar categoria", description = "Remove uma categoria pelo ID informado" )
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
