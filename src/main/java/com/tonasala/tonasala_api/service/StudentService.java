package com.tonasala.tonasala_api.service;

import com.tonasala.tonasala_api.dto.student.StudentCreateDTO;
import com.tonasala.tonasala_api.dto.student.StudentResponseDTO;
import com.tonasala.tonasala_api.dto.student.StudentUpdateDTO;
import com.tonasala.tonasala_api.entity.Institution;
import com.tonasala.tonasala_api.entity.Student;
import com.tonasala.tonasala_api.mapper.StudentMapper;
import com.tonasala.tonasala_api.repository.InstitutionRepository;
import com.tonasala.tonasala_api.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;
    private final InstitutionRepository institutionRepository;
    private final StudentMapper mapper;

    public StudentService(
            StudentRepository repository,
            InstitutionRepository institutionRepository,
            StudentMapper mapper
    ) {
        this.repository = repository;
        this.institutionRepository = institutionRepository;
        this.mapper = mapper;
    }

    @Transactional
    public StudentResponseDTO create(StudentCreateDTO dto) {
        // Evita duplicação de e-mail
        if (repository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado para outro estudante.");
        }

        // Verifica instituição
        Institution institution = institutionRepository.findById(dto.institutionId())
                .orElseThrow(() -> new IllegalArgumentException("Instituição não encontrada."));

        // Mapeia e associa instituição
        Student student = mapper.toEntity(dto);
        student.setInstitution(institution);

        // Persiste e retorna resposta
        Student saved = repository.save(student);
        return mapper.toResponseDto(saved);
    }

    @Transactional
    public StudentResponseDTO update(Long id, StudentUpdateDTO dto) {
        Student existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estudante não encontrado."));

        // Verifica se o novo e-mail já pertence a outro aluno
        if (dto.email() != null && !dto.email().equals(existing.getEmail())) {
            if (repository.existsByEmail(dto.email())) {
                throw new IllegalArgumentException("E-mail já cadastrado para outro estudante.");
            }
        }

        // Atualiza dados
        mapper.updateEntityFromDto(dto, existing);

        // Atualiza instituição, se necessário
        if (dto.institutionId() != null) {
            Institution institution = institutionRepository.findById(dto.institutionId())
                    .orElseThrow(() -> new IllegalArgumentException("Instituição não encontrada."));
            existing.setInstitution(institution);
        }

        Student updated = repository.save(existing);
        return mapper.toResponseDto(updated);
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO findById(Long id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estudante não encontrado."));
        return mapper.toResponseDto(student);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Estudante não encontrado.");
        }
        repository.deleteById(id);
    }
}
