package com.tonasala.tonasala_api.dto.institution;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InstitutionLoginDTO (
        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "Formato de e-mail inválido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        String password
) {}
