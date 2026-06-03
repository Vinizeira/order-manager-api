package dev.projectx.order_manager_api.controller;

import dev.projectx.order_manager_api.dto.LoginRequest;
import dev.projectx.order_manager_api.dto.LoginResponse;
import dev.projectx.order_manager_api.dto.RegisterRequest;
import dev.projectx.order_manager_api.dto.RegisterResponse;
import dev.projectx.order_manager_api.service.JwtService;
import dev.projectx.order_manager_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(
            UsuarioService usuarioService,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = usuarioService.registrar(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email().toLowerCase(),
                        request.senha()
                )
        );

        String token = jwtService.gerarToken(request.email().toLowerCase());

        return ResponseEntity.ok(new LoginResponse(token));
    }
}