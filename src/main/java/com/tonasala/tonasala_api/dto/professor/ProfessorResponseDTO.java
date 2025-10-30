package com.tonasala.tonasala_api.dto.professor;

import java.util.Set;

public record ProfessorResponseDTO(
        Long id,
        String name,
        String email,
        Set<Long> institutions // nomes das instituições associadas
) {}
