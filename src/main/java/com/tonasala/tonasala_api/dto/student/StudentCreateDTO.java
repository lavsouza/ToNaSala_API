package com.tonasala.tonasala_api.dto.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StudentCreateDTO(

        @NotBlank(message = "O nome é obrigatório.")
        String name,

        @NotBlank(message = "A matrícula é obrigatória.")
        String enrollment,

        @Email(message = "E-mail inválido.")
        @NotBlank(message = "O e-mail é obrigatório.")
        String email,

        @NotNull(message = "A instituição é obrigatória.")
        Long institutionId
) {}
