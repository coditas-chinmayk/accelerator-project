package com.example.accelerator.repository;

import com.example.accelerator.domain.entity.Assessment;
import com.example.accelerator.domain.entity.AssessmentQuestion;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentQuestionRepository extends JpaRepository<AssessmentQuestion,Long> {
    List<AssessmentQuestion> findAllByAssessment(Assessment assessment);
}
