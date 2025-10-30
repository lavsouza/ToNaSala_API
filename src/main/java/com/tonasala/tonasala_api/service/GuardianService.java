package com.tonasala.tonasala_api.service;

import com.tonasala.tonasala_api.dto.guardian.*;
import com.tonasala.tonasala_api.entity.Guardian;
import com.tonasala.tonasala_api.entity.Student;
import com.tonasala.tonasala_api.mapper.GuardianMapper;
import com.tonasala.tonasala_api.repository.GuardianRepository;
import com.tonasala.tonasala_api.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GuardianService {

    private final GuardianRepository repository;
    private final StudentRepository studentRepository;
    private final PasswordService passwordService;
    private final GuardianMapper mapper;

    public GuardianService(
            GuardianRepository repository,
            StudentRepository studentRepository,
            PasswordService passwordService,
            GuardianMapper mapper
    ) {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.passwordService = passwordService;
        this.mapper = mapper;
    }

    @Transactional
    public GuardianResponseDTO create(GuardianCreateDTO dto) {
        if (repository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado para outro guardião.");
        }

        Guardian guardian = mapper.toEntity(dto);
        guardian.setPassword(passwordService.hash(guardian.getPassword()));

        if (dto.studentIds() != null && !dto.studentIds().isEmpty()) {
            Set<Student> students = new HashSet<>(studentRepository.findAllById(dto.studentIds()));
            guardian.setStudents(students);
        }

        Guardian saved = repository.save(guardian);
        return mapper.toDTO(saved);
    }

    @Transactional
    public GuardianResponseDTO update(Long id, GuardianUpdateDTO dto) {
        Guardian existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Guardião não encontrado."));

        mapper.updateEntityFromDto(dto, existing);

        if (dto.password() != null && !dto.password().isBlank()) {
            existing.setPassword(passwordService.hash(dto.password()));
        }

        if (dto.studentIds() != null) {
            Set<Student> students = new HashSet<>(studentRepository.findAllById(dto.studentIds()));
            existing.setStudents(students);
        }

        Guardian updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    @Transactional(readOnly = true)
    public List<GuardianResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public GuardianResponseDTO findById(Long id) {
        Guardian guardian = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Guardião não encontrado."));
        return mapper.toDTO(guardian);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Guardião não encontrado.");
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public GuardianResponseDTO authenticate(GuardianLoginDTO dto) {
        Guardian guardian = repository.findByEmail(dto.email())
                .orElseThrow(() -> new IllegalArgumentException("E-mail não encontrado."));

        if (!passwordService.matches(dto.password(), guardian.getPassword())) {
            throw new IllegalArgumentException("Senha incorreta.");
        }

        return mapper.toDTO(guardian);
    }
}
