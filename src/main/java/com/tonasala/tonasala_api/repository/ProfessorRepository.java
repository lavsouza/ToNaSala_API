package com.tonasala.tonasala_api.repository;

import com.tonasala.tonasala_api.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
