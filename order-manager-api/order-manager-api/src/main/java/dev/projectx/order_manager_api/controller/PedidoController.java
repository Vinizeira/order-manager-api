package dev.projectx.order_manager_api.controller;

import dev.projectx.order_manager_api.dto.PedidoRequest;
import dev.projectx.order_manager_api.dto.PedidoResponse;
import dev.projectx.order_manager_api.model.Pedido;
import dev.projectx.order_manager_api.model.StatusPedido;
import dev.projectx.order_manager_api.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    public List<PedidoResponse> listar(){
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@RequestBody @Valid PedidoRequest request){
        return ResponseEntity.ok(service.criar(request));
    }

    @GetMapping("/status/{status}")
    public List<PedidoResponse> buscarPorStatus(@PathVariable StatusPedido status) {
        return service.buscarPorStatus(status);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<PedidoResponse> buscarPorCliente(@PathVariable Long clienteId) {
        return service.buscarPorCliente(clienteId);
    }

    @GetMapping("/periodo")
    public List<PedidoResponse> buscarPorPeriodo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {
        return service.buscarPorPeriodo(inicio, fim);
    }

}
