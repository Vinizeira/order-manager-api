package dev.projectx.order_manager_api.controller;

import dev.projectx.order_manager_api.dto.CategoriaRequest;
import dev.projectx.order_manager_api.dto.CategoriaResponse;
import dev.projectx.order_manager_api.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoriaResponse> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> salvar(@RequestBody @Valid CategoriaRequest request) {
        return ResponseEntity.ok(service.salvar(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
