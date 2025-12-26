package com.example.accelerator.repository;

import com.example.accelerator.domain.entity.QuestionCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionConditionRepository extends JpaRepository<QuestionCondition, Long> {
    boolean existsByQuestionId(Long questionId);
    Optional<QuestionCondition> findByQuestionId(Long questionId);
}
