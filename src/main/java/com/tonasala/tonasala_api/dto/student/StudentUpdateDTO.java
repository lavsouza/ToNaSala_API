package com.tonasala.tonasala_api.dto.student;

import jakarta.validation.constraints.Email;

public record StudentUpdateDTO(

        String name,

        String enrollment,

        @Email(message = "E-mail inválido.")
        String email,

        Long institutionId
) {}
