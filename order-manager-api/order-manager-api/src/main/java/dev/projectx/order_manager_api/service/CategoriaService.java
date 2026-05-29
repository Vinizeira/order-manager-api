package dev.projectx.order_manager_api.service;

import dev.projectx.order_manager_api.dto.CategoriaRequest;
import dev.projectx.order_manager_api.dto.CategoriaResponse;
import dev.projectx.order_manager_api.model.Categoria;
import dev.projectx.order_manager_api.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.repository = categoriaRepository;
    }

    public List<CategoriaResponse> listar() {
        return repository.findAll()
                .stream()
                .map(c -> new CategoriaResponse(c.getId(), c.getNome()))
                .toList();
    }

    public CategoriaResponse buscarPorId(Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
                return new CategoriaResponse(categoria.getId(), categoria.getNome());
    }

    public CategoriaResponse salvar(CategoriaRequest request) {
       Categoria categoria = new Categoria(request.nome());
        repository.save(categoria);
        return new CategoriaResponse(categoria.getId(), categoria.getNome());
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
