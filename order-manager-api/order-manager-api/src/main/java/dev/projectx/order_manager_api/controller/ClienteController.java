package dev.projectx.order_manager_api.controller;

import dev.projectx.order_manager_api.dto.ClienteRequest;
import dev.projectx.order_manager_api.dto.ClienteResponse;
import dev.projectx.order_manager_api.model.Cliente;
import dev.projectx.order_manager_api.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<ClienteResponse> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> salvar(@RequestBody @Valid ClienteRequest cliente){
        return ResponseEntity.ok(service.salvar(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
