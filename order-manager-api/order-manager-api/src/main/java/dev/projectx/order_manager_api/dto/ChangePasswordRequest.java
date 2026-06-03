package dev.projectx.order_manager_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotBlank(message = "A senha atual e obrigatoria")
        String senhaAtual,

        @NotBlank(message = "A nova senha e obrigatoria")
        @Size(min = 6, message = "A nova senha deve ter pelo menos 6 caracteres")
        String novaSenha
) {}
