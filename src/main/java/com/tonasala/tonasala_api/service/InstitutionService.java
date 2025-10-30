package com.tonasala.tonasala_api.service;

import com.tonasala.tonasala_api.dto.*;
import com.tonasala.tonasala_api.dto.institution.InstitutionCreateDTO;
import com.tonasala.tonasala_api.dto.institution.InstitutionLoginDTO;
import com.tonasala.tonasala_api.dto.institution.InstitutionResponseDTO;
import com.tonasala.tonasala_api.dto.institution.InstitutionUpdateDTO;
import com.tonasala.tonasala_api.entity.Institution;
import com.tonasala.tonasala_api.mapper.InstitutionMapper;
import com.tonasala.tonasala_api.repository.InstitutionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InstitutionService {

    private final InstitutionRepository repository;
    private final PasswordService passwordService;
    private final InstitutionMapper mapper;

    public InstitutionService(
            InstitutionRepository repository,
            PasswordService passwordService,
            InstitutionMapper mapper
    ) {
        this.repository = repository;
        this.passwordService = passwordService;
        this.mapper = mapper;
    }

    @Transactional
    public InstitutionResponseDTO create(InstitutionCreateDTO dto) {
        if (repository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado para outra instituição.");
        }

        Institution institution = mapper.toEntity(dto);
        institution.setPassword(passwordService.hash(institution.getPassword()));

        Institution saved = repository.save(institution);
        return mapper.toResponseDto(saved);
    }

    @Transactional
    public InstitutionResponseDTO update(Long id, InstitutionUpdateDTO dto) {
        Institution existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instituição não encontrada."));

        mapper.updateEntityFromDto(dto, existing);

        if (dto.password() != null && !dto.password().isBlank()) {
            existing.setPassword(passwordService.hash(dto.password()));
        }

        Institution updated = repository.save(existing);
        return mapper.toResponseDto(updated);
    }

    @Transactional(readOnly = true)
    public List<InstitutionResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public InstitutionResponseDTO findById(Long id) {
        Institution institution = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instituição não encontrada."));
        return mapper.toResponseDto(institution);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Instituição não encontrada.");
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public InstitutionResponseDTO authenticate(InstitutionLoginDTO dto) {
        Institution institution = repository.findByEmail(dto.email())
                .orElseThrow(() -> new IllegalArgumentException("E-mail não encontrado."));

        if (!passwordService.matches(dto.password(), institution.getPassword())) {
            throw new IllegalArgumentException("Senha incorreta.");
        }

        return mapper.toResponseDto(institution);
    }
}
