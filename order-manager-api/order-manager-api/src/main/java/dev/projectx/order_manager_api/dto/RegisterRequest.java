package dev.projectx.order_manager_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank String nome,
        @Email String email,
        @NotBlank String senha
) {}
