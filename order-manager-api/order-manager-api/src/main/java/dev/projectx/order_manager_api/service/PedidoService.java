package dev.projectx.order_manager_api.service;

import dev.projectx.order_manager_api.dto.ItemPedidoRequest;
import dev.projectx.order_manager_api.dto.PedidoRequest;
import dev.projectx.order_manager_api.dto.PedidoResponse;
import dev.projectx.order_manager_api.exception.BusinessException;
import dev.projectx.order_manager_api.exception.ResourceNotFoundException;
import dev.projectx.order_manager_api.model.*;
import dev.projectx.order_manager_api.repository.ClienteRepository;
import dev.projectx.order_manager_api.repository.PedidoRepository;
import dev.projectx.order_manager_api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    public PedidoResponse criar(PedidoRequest request){
        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        Pedido pedido = new Pedido(cliente);

        for (ItemPedidoRequest itemRequest : request.itens()) {
            Produto produto = produtoRepository.findById(itemRequest.produtoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

            if (produto.getEstoque() < itemRequest.quantidade()) {
                throw new BusinessException("Estoque insuficiente para: " + produto.getNome());
            }
            ItemPedido item = new ItemPedido(pedido, produto, itemRequest.quantidade());
            pedido.getItens().add(item);

            produto.setEstoque(produto.getEstoque() - itemRequest.quantidade());
            pedidoRepository.save(pedido);
        }

            BigDecimal total = pedido.getItens().stream()
                    .map(i -> i.getPrecoUnitario().multiply(BigDecimal.valueOf(i.getQuantidade())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            pedido.setTotal(total);
            pedidoRepository.save(pedido);

            return new PedidoResponse(
                    pedido.getId(),
                    cliente.getNome(),
                    pedido.getData(),
                    pedido.getStatus().name(),
                    pedido.getTotal()
            );
    }
    public List<PedidoResponse> listar(){
        return pedidoRepository.findAll()
                .stream()
                .map(p -> new PedidoResponse(
                        p.getId(),
                        p.getCliente().getNome(),
                        p.getData(),
                        p.getStatus().name(),
                        p.getTotal()))
                .toList();
    }

    public List<PedidoResponse> buscarPorStatus(StatusPedido status){
        return pedidoRepository.findByStatus(status)
                .stream()
                .map(p -> new PedidoResponse(
                        p.getId(),
                        p.getCliente().getNome(),
                        p.getData(),
                        p.getStatus().name(),
                        p.getTotal()))
                .toList();
    }

    public List<PedidoResponse> buscarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId)
                .stream()
                .map(p -> new PedidoResponse(
                        p.getId(),
                        p.getCliente().getNome(),
                        p.getData(),
                        p.getStatus().name(),
                        p.getTotal()))
                .toList();
    }

    public List<PedidoResponse> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return pedidoRepository.findByDataBetween(inicio, fim)
                .stream()
                .map(p -> new PedidoResponse(
                        p.getId(),
                        p.getCliente().getNome(),
                        p.getData(),
                        p.getStatus().name(),
                        p.getTotal()))
                .toList();
    }

}
