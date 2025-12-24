package com.example.accelerator.repository;

import com.example.accelerator.domain.entity.Assessment;
import com.example.accelerator.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment,Long> {

    List<Assessment> findAllByCategory(Category category);

    Optional<Assessment> findByName(String name);
}
