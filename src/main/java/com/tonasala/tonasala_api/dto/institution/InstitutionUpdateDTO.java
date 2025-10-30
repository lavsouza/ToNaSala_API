package com.tonasala.tonasala_api.dto.institution;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record InstitutionUpdateDTO(
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
        String name,

        @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter exatamente 14 dígitos numéricos.")
        String cnpj,

        @Email(message = "Formato de e-mail inválido.")
        String email,

        @Size(min = 6, max = 60, message = "A senha deve ter entre 6 e 60 caracteres.")
        String password
) {}
