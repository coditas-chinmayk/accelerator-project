package com.example.accelerator.repository;

import com.example.accelerator.domain.entity.QuestionCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionConditionRepository extends JpaRepository<QuestionCondition, Long> {
    // Fetch condition if exists for a specific question
    Optional<QuestionCondition> findByQuestion_Id(Long questionId);

    // Fetch all conditions for questions belonging to a specific assessment
    List<QuestionCondition> findByQuestion_Assessment_Id(Long assessmentId);
    boolean existsByQuestionId(Long questionId);
}
