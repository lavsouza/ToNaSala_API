package com.tonasala.tonasala_api.controller;

import com.tonasala.tonasala_api.dto.institution.InstitutionCreateDTO;
import com.tonasala.tonasala_api.dto.institution.InstitutionLoginDTO;
import com.tonasala.tonasala_api.dto.institution.InstitutionResponseDTO;
import com.tonasala.tonasala_api.dto.institution.InstitutionUpdateDTO;
import com.tonasala.tonasala_api.service.InstitutionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/institutions")
public class InstitutionController {

    private final InstitutionService service;

    public InstitutionController(InstitutionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InstitutionResponseDTO> create(@Valid @RequestBody InstitutionCreateDTO dto) {
        InstitutionResponseDTO created = service.create(dto);
        URI location = URI.create("/api/institutions/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstitutionResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody InstitutionUpdateDTO dto
    ) {
        InstitutionResponseDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<InstitutionResponseDTO>> findAll() {
        List<InstitutionResponseDTO> institutions = service.findAll();
        return ResponseEntity.ok(institutions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstitutionResponseDTO> findById(@PathVariable Long id) {
        InstitutionResponseDTO institution = service.findById(id);
        return ResponseEntity.ok(institution);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<InstitutionResponseDTO> login(@Valid @RequestBody InstitutionLoginDTO dto) {
        InstitutionResponseDTO institution = service.authenticate(dto);
        return ResponseEntity.ok(institution);
    }
}
