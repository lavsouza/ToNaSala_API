package com.tonasala.tonasala_api.mapper;

import com.tonasala.tonasala_api.dto.professor.*;
import com.tonasala.tonasala_api.entity.Professor;
import com.tonasala.tonasala_api.entity.Institution;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProfessorMapper {

    public Professor toEntity(ProfessorCreateDTO dto) {
        if (dto == null) return null;

        Professor professor = new Professor();
        professor.setName(dto.name());
        professor.setEmail(dto.email());
        professor.setPassword(dto.password());
        professor.setInstitution(new HashSet<>());
        return professor;
    }

    public void updateEntityFromDto(ProfessorUpdateDTO dto, Professor professor) {
        if (dto == null || professor == null) return;

        if (dto.name() != null) professor.setName(dto.name());
        if (dto.email() != null) professor.setEmail(dto.email());
        if (dto.password() != null && !dto.password().isBlank()) {
            professor.setPassword(dto.password());
        }
    }

    public ProfessorResponseDTO toDTO(Professor professor) {
        if (professor == null) return null;

        Set<Long> institutionIds = professor.getInstitutions()
                .stream()
                .map(Institution::getId)
                .collect(Collectors.toSet());

        return new ProfessorResponseDTO(
                professor.getId(),
                professor.getName(),
                professor.getEmail(),
                institutionIds
        );
    }
}