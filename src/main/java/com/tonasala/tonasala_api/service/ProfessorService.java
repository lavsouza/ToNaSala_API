package com.tonasala.tonasala_api.service;

import com.tonasala.tonasala_api.dto.professor.*;
import com.tonasala.tonasala_api.entity.Institution;
import com.tonasala.tonasala_api.entity.Professor;
import com.tonasala.tonasala_api.mapper.ProfessorMapper;
import com.tonasala.tonasala_api.repository.InstitutionRepository;
import com.tonasala.tonasala_api.repository.ProfessorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProfessorService {

    private final ProfessorRepository repository;
    private final InstitutionRepository institutionRepository;
    private final PasswordService passwordService;
    private final ProfessorMapper mapper;

    public ProfessorService(
            ProfessorRepository repository,
            InstitutionRepository institutionRepository,
            PasswordService passwordService,
            ProfessorMapper mapper
    ) {
        this.repository = repository;
        this.institutionRepository = institutionRepository;
        this.passwordService = passwordService;
        this.mapper = mapper;
    }

    @Transactional
    public ProfessorResponseDTO create(ProfessorCreateDTO dto) {
        if (repository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado para outro professor.");
        }

        Professor professor = mapper.toEntity(dto);

        professor.setPassword(passwordService.hash(dto.password()));

        if (dto.institutionIds() != null && !dto.institutionIds().isEmpty()) {
            Set<Institution> institutions = new HashSet<>(institutionRepository.findAllById(dto.institutionIds()));
            professor.setInstitution(institutions);
        }

        Professor saved = repository.save(professor);
        return mapper.toDTO(saved);
    }

    @Transactional
    public ProfessorResponseDTO update(Long id, ProfessorUpdateDTO dto) {
        Professor existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado."));

        if (dto.email() != null && !dto.email().equals(existing.getEmail())) {
            if (repository.existsByEmail(dto.email())) {
                throw new IllegalArgumentException("E-mail já cadastrado para outro professor.");
            }
        }

        mapper.updateEntityFromDto(dto, existing);

        if (dto.password() != null && !dto.password().isBlank()) {
            existing.setPassword(passwordService.hash(dto.password()));
        }

        if (dto.institutionIds() != null) {
            Set<Institution> institutions = new HashSet<>(institutionRepository.findAllById(dto.institutionIds()));
            existing.setInstitution(institutions);
        }

        Professor updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    @Transactional(readOnly = true)
    public List<ProfessorResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProfessorResponseDTO findById(Long id) {
        Professor professor = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado."));
        return mapper.toDTO(professor);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Professor não encontrado.");
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ProfessorResponseDTO authenticate(ProfessorLoginDTO dto) {
        Professor professor = repository.findByEmail(dto.email())
                .orElseThrow(() -> new IllegalArgumentException("E-mail não encontrado."));

        if (!passwordService.matches(dto.password(), professor.getPassword())) {
            throw new IllegalArgumentException("Senha incorreta.");
        }

        return mapper.toDTO(professor);
    }
}