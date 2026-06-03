package dev.projectx.order_manager_api.controller;

import dev.projectx.order_manager_api.dto.ChangePasswordRequest;
import dev.projectx.order_manager_api.dto.UsuarioResponse;
import dev.projectx.order_manager_api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Endpoints para gerenciamento de usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Retorna todos os usuarios cadastrados")
    public List<UsuarioResponse> listar() {
        return usuarioService.listar();
    }

    @PatchMapping("/me/senha")
    @Operation(summary = "Alterar minha senha", description = "Altera a senha do usuario autenticado")
    public ResponseEntity<UsuarioResponse> alterarMinhaSenha(
            Authentication authentication,
            @RequestBody @Valid ChangePasswordRequest request
    ) {
        return ResponseEntity.ok(usuarioService.alterarSenha(authentication.getName(), request));
    }
}
