package com.tonasala.tonasala_api.dto.professor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record ProfessorUpdateDTO(
        String name,

        @Email(message = "E-mail inválido.")
        String email,

        @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
        String password,

        Set<Long> institutionIds
) {}
