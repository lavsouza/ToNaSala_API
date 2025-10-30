package com.tonasala.tonasala_api.controller;

import com.tonasala.tonasala_api.dto.professor.*;
import com.tonasala.tonasala_api.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/professors")
public class ProfessorController {

    private final ProfessorService service;

    public ProfessorController(ProfessorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> create(@Valid @RequestBody ProfessorCreateDTO dto) {
        ProfessorResponseDTO created = service.create(dto);
        URI location = URI.create("/api/professors/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProfessorUpdateDTO dto
    ) {
        ProfessorResponseDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> findAll() {
        List<ProfessorResponseDTO> professors = service.findAll();
        return ResponseEntity.ok(professors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> findById(@PathVariable Long id) {
        ProfessorResponseDTO professor = service.findById(id);
        return ResponseEntity.ok(professor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<ProfessorResponseDTO> login(@Valid @RequestBody ProfessorLoginDTO dto) {
        ProfessorResponseDTO authenticated = service.authenticate(dto);
        return ResponseEntity.ok(authenticated);
    }
}