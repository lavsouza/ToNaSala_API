package com.tonasala.tonasala_api.dto.guardian;

import java.util.Set;

public record GuardianReponseDTO(
        Long id,
        String name,
        String email,
        String cpf,
        Set<Long> studentIds
) { }
