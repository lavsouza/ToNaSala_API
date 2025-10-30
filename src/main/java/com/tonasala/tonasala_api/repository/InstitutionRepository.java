package com.tonasala.tonasala_api.repository;

import com.tonasala.tonasala_api.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long>{
    boolean existsByEmail(String email);
    Optional<Institution> findByEmail(String email);
}
