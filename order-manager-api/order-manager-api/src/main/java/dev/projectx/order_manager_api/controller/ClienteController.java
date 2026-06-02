package dev.projectx.order_manager_api.controller;

import dev.projectx.order_manager_api.dto.ClienteRequest;
import dev.projectx.order_manager_api.dto.ClienteResponse;
import dev.projectx.order_manager_api.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(
            summary = "Listar Clientes",
            description = "Retorna todos os clientes cadastrados"
    )
    public List<ClienteResponse> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar cliente por ID",
            description = "Retorna um cliente específico pelo ID informado"
    )
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(
            summary = "Cadastrar Cliente",
            description = "Cria um novo cliente no sistema"
    )
    public ResponseEntity<ClienteResponse> salvar(@RequestBody @Valid ClienteRequest cliente){
        return ResponseEntity.ok(service.salvar(cliente));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar Cliente",
            description = "Remove um cliente pelo ID informado"
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
