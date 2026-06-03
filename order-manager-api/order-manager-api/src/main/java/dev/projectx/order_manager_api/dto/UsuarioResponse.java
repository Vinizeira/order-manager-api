package dev.projectx.order_manager_api.dto;

import dev.projectx.order_manager_api.model.Role;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        Role role
) {}
