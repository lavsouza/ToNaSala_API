package com.tonasala.tonasala_api.controller;

import com.tonasala.tonasala_api.dto.student.StudentCreateDTO;
import com.tonasala.tonasala_api.dto.student.StudentResponseDTO;
import com.tonasala.tonasala_api.dto.student.StudentUpdateDTO;
import com.tonasala.tonasala_api.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> create(@Valid @RequestBody StudentCreateDTO dto) {
        StudentResponseDTO created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody StudentUpdateDTO dto
    ) {
        StudentResponseDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> findAll() {
        List<StudentResponseDTO> students = service.findAll();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> findById(@PathVariable Long id) {
        StudentResponseDTO student = service.findById(id);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
