package dev.projectx.order_manager_api.service;

import dev.projectx.order_manager_api.dto.ChangePasswordRequest;
import dev.projectx.order_manager_api.dto.RegisterRequest;
import dev.projectx.order_manager_api.dto.RegisterResponse;
import dev.projectx.order_manager_api.dto.UsuarioResponse;
import dev.projectx.order_manager_api.exception.BusinessException;
import dev.projectx.order_manager_api.exception.ResourceNotFoundException;
import dev.projectx.order_manager_api.model.Role;
import dev.projectx.order_manager_api.model.Usuario;
import dev.projectx.order_manager_api.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.repository = usuarioRepository;
        this.encoder = passwordEncoder;
    }

    public RegisterResponse registrar(RegisterRequest request) {
        if (repository.findByEmail(request.email()).isPresent()) {
            throw new BusinessException("Email já cadastrado");
        }

        Usuario usuario = new Usuario(
                request.nome(),
                request.email().toLowerCase(),
                encoder.encode(request.senha()),
                Role.ROLE_USER
        );

        Usuario salvo = repository.save(usuario);

        return new RegisterResponse(
                salvo.getId(),
                salvo.getNome(),
                salvo.getEmail(),
                salvo.getRole()
        );
    }

    public List<UsuarioResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UsuarioResponse alterarSenha(String email, ChangePasswordRequest request) {
        Usuario usuario = repository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado"));

        if (!encoder.matches(request.senhaAtual(), usuario.getSenha())) {
            throw new BusinessException("Senha atual incorreta");
        }

        usuario.setSenha(encoder.encode(request.novaSenha()));
        Usuario salvo = repository.save(usuario);

        return toResponse(salvo);
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole()
        );
    }
}
