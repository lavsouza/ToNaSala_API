package com.tonasala.tonasala_api.mapper;

import com.tonasala.tonasala_api.dto.student.StudentCreateDTO;
import com.tonasala.tonasala_api.dto.student.StudentResponseDTO;
import com.tonasala.tonasala_api.dto.student.StudentUpdateDTO;
import com.tonasala.tonasala_api.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public Student toEntity(StudentCreateDTO dto) {
        if (dto == null) return null;
        Student student = new Student();
        student.setName(dto.name());
        student.setEnrollment(dto.enrollment());
        student.setEmail(dto.email());
        return student;
    }

    public void updateEntityFromDto(StudentUpdateDTO dto, Student student) {
        if (dto == null || student == null) return;
        if (dto.name() != null) student.setName(dto.name());
        if (dto.enrollment() != null) student.setEnrollment(dto.enrollment());
        if (dto.email() != null) student.setEmail(dto.email());
    }

    public StudentResponseDTO toResponseDto(Student student) {
        if (student == null) return null;
        return new StudentResponseDTO(
                student.getId(),
                student.getName(),
                student.getEnrollment(),
                student.getEmail(),
                student.getInstitution() != null ? student.getInstitution().getName() : null
        );
    }
}
