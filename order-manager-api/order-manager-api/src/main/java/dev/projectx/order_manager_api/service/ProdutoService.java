package dev.projectx.order_manager_api.service;

import dev.projectx.order_manager_api.dto.ProdutoRequest;
import dev.projectx.order_manager_api.dto.ProdutoResponse;
import dev.projectx.order_manager_api.exception.ResourceNotFoundException;
import dev.projectx.order_manager_api.model.Categoria;
import dev.projectx.order_manager_api.model.Produto;
import dev.projectx.order_manager_api.repository.CategoriaRepository;
import dev.projectx.order_manager_api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository repository,
                          CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<ProdutoResponse> listar() {
        return repository.findAll()
                .stream()
                .map(p -> new ProdutoResponse(p.getId(), p.getNome(), p.getPreco(), p.getEstoque(), p.getCategoria().getNome()))
                .toList();
    }

    public ProdutoResponse buscarPorId(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
                return new ProdutoResponse(produto.getId(),
                        produto.getNome(),
                        produto.getPreco(),
                        produto.getEstoque(),
                        produto.getCategoria().getNome());
    }

    public ProdutoResponse salvar(ProdutoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        Produto produto = new Produto(request.nome(), request.preco(), request.estoque(), categoria);
        repository.save(produto);
        return new ProdutoResponse(produto.getId(), produto.getNome(), produto.getPreco(), produto.getEstoque(), produto.getCategoria().getNome());
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public List<ProdutoResponse> buscarPorCategoria(Long categoriaId) {
        return repository.findByCategoriaId(categoriaId)
                .stream()
                .map(p -> new ProdutoResponse(p.getId(), p.getNome(), p.getPreco(), p.getEstoque(), p.getCategoria().getNome()))
                .toList();
    }

    public List<ProdutoResponse> buscarEstoqueBaixo(Integer quantidade) {
        return repository.findByEstoqueLessThan(quantidade)
                .stream()
                .map(p -> new ProdutoResponse(p.getId(), p.getNome(), p.getPreco(), p.getEstoque(), p.getCategoria().getNome()))
                .toList();
    }
}