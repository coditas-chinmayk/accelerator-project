package com.example.accelerator.repository;

import com.example.accelerator.domain.entity.AssessmentQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentQuestionRepository extends JpaRepository<AssessmentQuestion,Long> {
}
