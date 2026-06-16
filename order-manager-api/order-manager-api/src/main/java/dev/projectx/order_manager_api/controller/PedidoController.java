package dev.projectx.order_manager_api.controller;

import dev.projectx.order_manager_api.dto.AtualizarStatusPedidoRequest;
import dev.projectx.order_manager_api.dto.PedidoRequest;
import dev.projectx.order_manager_api.dto.PedidoResponse;
import dev.projectx.order_manager_api.model.StatusPedido;
import dev.projectx.order_manager_api.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
public class PedidoController {
    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }


    @GetMapping
    @Operation(summary = "Listar pedidos", description = "Retorna todos os pedidos cadastrados")
    public List<PedidoResponse> listar() {
        return service.listar();
    }

    @PostMapping
    @Operation(summary = "Criar pedido", description = "Cria um novo pedido com um ou mais itens")
    public ResponseEntity<PedidoResponse> criar(@RequestBody @Valid PedidoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Buscar pedidos por status", description = "Retorna todos os pedidos com o status informado")
    public List<PedidoResponse> buscarPorStatus(@PathVariable StatusPedido status) {
        return service.buscarPorStatus(status);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Buscar pedidos por cliente", description = "Retorna todos os pedidos de um cliente específico")
    public List<PedidoResponse> buscarPorCliente(@PathVariable Long clienteId) {
        return service.buscarPorCliente(clienteId);
    }

    @GetMapping("/periodo")
    @Operation(summary = "Buscar pedidos por período", description = "Retorna pedidos criados entre as datas informadas")
    public List<PedidoResponse> buscarPorPeriodo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {
        return service.buscarPorPeriodo(inicio, fim);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do pedido", description = "Altera o status do pedido e devolve estoque em caso de cancelamento")
    public ResponseEntity<PedidoResponse> atualizarStatus(
            @PathVariable Long id,
            @RequestBody @Valid AtualizarStatusPedidoRequest request) {
        return ResponseEntity.ok(service.atualizarStatus(id, request));
    }
}
