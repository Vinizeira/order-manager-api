package dev.projectx.order_manager_api.service;

import dev.projectx.order_manager_api.dto.AtualizarStatusPedidoRequest;
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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
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

    @Transactional
    public PedidoResponse atualizarStatus(Long id, AtualizarStatusPedidoRequest request) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        StatusPedido statusAtual = pedido.getStatus();
        StatusPedido novoStatus = request.status();

        if (statusAtual == novoStatus) {
            return toResponse(pedido);
        }

        if (statusAtual == StatusPedido.ENTREGUE || statusAtual == StatusPedido.CANCELADO) {
            throw new BusinessException("Pedidos entregues ou cancelados não podem ser alterados");
        }

        if (!transicaoValida(statusAtual, novoStatus)) {
            throw new BusinessException("Transição de status inválida: " + statusAtual + " para " + novoStatus);
        }

        if (novoStatus == StatusPedido.CANCELADO) {
            devolverEstoque(pedido);
        }

        pedido.setStatus(novoStatus);
        Pedido salvo = pedidoRepository.save(pedido);

        return toResponse(salvo);
    }

    private boolean transicaoValida(StatusPedido statusAtual, StatusPedido novoStatus) {
        if (novoStatus == StatusPedido.CANCELADO) {
            return statusAtual != StatusPedido.ENTREGUE && statusAtual != StatusPedido.CANCELADO;
        }

        return switch (statusAtual) {
            case PENDENTE -> novoStatus == StatusPedido.PAGO;
            case PAGO -> novoStatus == StatusPedido.ENVIADO;
            case ENVIADO -> novoStatus == StatusPedido.ENTREGUE;
            case ENTREGUE, CANCELADO -> false;
        };
    }

    private void devolverEstoque(Pedido pedido) {
        for (ItemPedido item : pedido.getItens()) {
            Produto produto = item.getProduto();
            produto.setEstoque(produto.getEstoque() + item.getQuantidade());
            produtoRepository.save(produto);
        }
    }

    private PedidoResponse toResponse(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(),
                pedido.getCliente().getNome(),
                pedido.getData(),
                pedido.getStatus().name(),
                pedido.getTotal()
        );
    }

}
