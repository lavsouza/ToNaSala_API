package com.tonasala.tonasala_api.dto.guardian;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record GuardianUpdateDTO(
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
        String name,

        @Email(message = "E-mail inválido.")
        String email,

        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato XXX.XXX.XXX-XX")
        String cpf,

        @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
        String password,

        Set<Long> studentIds
) { }
