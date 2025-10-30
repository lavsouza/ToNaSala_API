package com.tonasala.tonasala_api.repository;

import com.tonasala.tonasala_api.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long>{
}
