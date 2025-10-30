package com.tonasala.tonasala_api.mapper;

import com.tonasala.tonasala_api.dto.*;
import com.tonasala.tonasala_api.dto.institution.InstitutionCreateDTO;
import com.tonasala.tonasala_api.dto.institution.InstitutionResponseDTO;
import com.tonasala.tonasala_api.dto.institution.InstitutionUpdateDTO;
import com.tonasala.tonasala_api.entity.Institution;
import org.springframework.stereotype.Component;

@Component
public class InstitutionMapper {

    public Institution toEntity(InstitutionCreateDTO dto) {
        if (dto == null) return null;

        Institution institution = new Institution();
        institution.setName(dto.name());
        institution.setCnpj(dto.cnpj());
        institution.setEmail(dto.email());
        institution.setPassword(dto.password());
        return institution;
    }

    public void updateEntityFromDto(InstitutionUpdateDTO dto, Institution institution) {
        if (dto == null || institution == null) return;

        if (dto.name() != null) institution.setName(dto.name());
        if (dto.cnpj() != null) institution.setCnpj(dto.cnpj());
        if (dto.email() != null) institution.setEmail(dto.email());
        if (dto.password() != null && !dto.password().isBlank()) {
            institution.setPassword(dto.password());
        }
    }

    public InstitutionResponseDTO toResponseDto(Institution institution) {
        if (institution == null) return null;

        return new InstitutionResponseDTO(
                institution.getId(),
                institution.getName(),
                institution.getCnpj(),
                institution.getEmail()
        );
    }
}
