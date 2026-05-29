package dev.projectx.order_manager_api.service;

import dev.projectx.order_manager_api.dto.ClienteRequest;
import dev.projectx.order_manager_api.dto.ClienteResponse;
import dev.projectx.order_manager_api.model.Cliente;
import dev.projectx.order_manager_api.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<ClienteResponse> listar() {
        return repository.findAll()
                .stream()
                .map(c -> new ClienteResponse(c.getId(), c.getNome(),
                        c.getEmail(),
                        c.getCpfFormatado(),
                        c.getTelefoneFormatado()))
                .toList();
    }

    public ClienteResponse buscarPorId(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
                return new ClienteResponse(cliente.getId(),
                        cliente.getNome(),
                        cliente.getEmail(),
                        cliente.getCpfFormatado(), cliente.getTelefoneFormatado());
    }

    public ClienteResponse salvar(ClienteRequest request) {
        Cliente cliente = new Cliente(request.nome(), request.email(), request.telefone(), request.cpf());
        repository.save(cliente);
        return new ClienteResponse(cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpfFormatado(),
                cliente.getTelefoneFormatado());
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

}
