package com.tonasala.tonasala_api.repository;

import com.tonasala.tonasala_api.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuardianRepository extends JpaRepository<Institution, Long> {
}
