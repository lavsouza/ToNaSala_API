package com.tonasala.tonasala_api.mapper;

import com.tonasala.tonasala_api.dto.guardian.*;
import com.tonasala.tonasala_api.entity.Guardian;
import com.tonasala.tonasala_api.entity.Student;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GuardianMapper {

    public Guardian toEntity(GuardianCreateDTO dto) {
        if (dto == null) return null;

        Guardian guardian = new Guardian();
        guardian.setName(dto.name());
        guardian.setEmail(dto.email());
        guardian.setCpf(dto.cpf());
        guardian.setPassword(dto.password());
        guardian.setStudents(new HashSet<>());
        return guardian;
    }

    public void updateEntityFromDto(GuardianUpdateDTO dto, Guardian guardian) {
        if (dto == null || guardian == null) return;

        if (dto.name() != null) guardian.setName(dto.name());
        if (dto.email() != null) guardian.setEmail(dto.email());
        if (dto.cpf() != null) guardian.setCpf(dto.cpf());
        if (dto.password() != null && !dto.password().isBlank()) {
            guardian.setPassword(dto.password());
        }
    }

    public GuardianResponseDTO toDTO(Guardian guardian) {
        if (guardian == null) return null;

        Set<Long> studentIds = guardian.getStudents()
                .stream()
                .map(Student::getId)
                .collect(Collectors.toSet());

        return new GuardianResponseDTO(
                guardian.getId(),
                guardian.getName(),
                guardian.getEmail(),
                guardian.getCpf(),
                studentIds
        );
    }
}
