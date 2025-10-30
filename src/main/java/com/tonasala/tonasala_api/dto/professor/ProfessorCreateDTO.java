package com.tonasala.tonasala_api.dto.professor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record ProfessorCreateDTO(
        @NotBlank(message = "O nome é obrigatório.")
        String name,

        @Email(message = "E-mail inválido.")
        @NotBlank(message = "O e-mail é obrigatório.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
        String password,

        @NotEmpty(message = "Deve haver pelo menos uma instituição associada.")
        Set<Long> institutionIds
) {}
