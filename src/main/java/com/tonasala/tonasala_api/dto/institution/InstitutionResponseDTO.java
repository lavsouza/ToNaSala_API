package com.tonasala.tonasala_api.dto.institution;

public record InstitutionResponseDTO(
        Long id,
        String name,
        String cnpj,
        String email
) { }
