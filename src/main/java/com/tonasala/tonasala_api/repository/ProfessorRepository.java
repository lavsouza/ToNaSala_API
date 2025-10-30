package com.tonasala.tonasala_api.repository;

import com.tonasala.tonasala_api.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    boolean existsByEmail(String email);
    Optional<Professor> findByEmail(String email);
}
