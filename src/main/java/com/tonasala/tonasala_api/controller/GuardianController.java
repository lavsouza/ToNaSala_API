package com.tonasala.tonasala_api.controller;

import com.tonasala.tonasala_api.dto.guardian.*;
import com.tonasala.tonasala_api.service.GuardianService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/guardians")
public class GuardianController {

    private final GuardianService service;

    public GuardianController(GuardianService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<GuardianResponseDTO> create(@Valid @RequestBody GuardianCreateDTO dto) {
        GuardianResponseDTO created = service.create(dto);
        URI location = URI.create("/api/guardians/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuardianResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody GuardianUpdateDTO dto
    ) {
        GuardianResponseDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<GuardianResponseDTO>> findAll() {
        List<GuardianResponseDTO> guardians = service.findAll();
        return ResponseEntity.ok(guardians);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuardianResponseDTO> findById(@PathVariable Long id) {
        GuardianResponseDTO guardian = service.findById(id);
        return ResponseEntity.ok(guardian);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<GuardianResponseDTO> login(@Valid @RequestBody GuardianLoginDTO dto) {
        GuardianResponseDTO authenticated = service.authenticate(dto);
        return ResponseEntity.ok(authenticated);
    }
}
