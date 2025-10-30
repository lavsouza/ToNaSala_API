package com.tonasala.tonasala_api.dto.guardian;

import java.util.Set;

public record GuardianResponseDTO(
        Long id,
        String name,
        String email,
        String cpf,
        Set<Long> studentIds
) { }
