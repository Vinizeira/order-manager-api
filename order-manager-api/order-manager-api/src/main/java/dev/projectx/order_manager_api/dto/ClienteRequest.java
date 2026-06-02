package dev.projectx.order_manager_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteRequest(

        @Schema(description = "Nome do Cliente", example = "Lucas")
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @Schema( description = "Email do cliente", example = "lucas@gmail.com" )
        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido!")
        String email,

        @Schema( description = "Telefone do cliente", example = "11999999999" )
        @NotBlank(message = "Telefone é obrigatório")
        String telefone,

        @Schema( description = "CPF do cliente", example = "12345678900" )
        @NotBlank(message = "CPF é Obrigatório")
        @CPF(message = "CPF inválido")
        String cpf
) {}
