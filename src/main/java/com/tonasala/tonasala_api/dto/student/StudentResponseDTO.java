package com.tonasala.tonasala_api.dto.student;

public record StudentResponseDTO(
        Long id,
        String name,
        String enrollment,
        String email,
        String institutionName
) {}
