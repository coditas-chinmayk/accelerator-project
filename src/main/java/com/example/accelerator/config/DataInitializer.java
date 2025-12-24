package com.example.accelerator.config;

import com.example.accelerator.domain.entity.Assessment;
import com.example.accelerator.domain.entity.AssessmentQuestion;
import com.example.accelerator.domain.enums.AssessmentStatus;
import com.example.accelerator.domain.enums.QuestionType;
import com.example.accelerator.repository.AssessmentQuestionRepository;
import com.example.accelerator.repository.AssessmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Profile({"dev", "local"})
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private AssessmentQuestionRepository questionRepository;


    @Override
    public void run(String... args) {

        // Create COMMON assessment if not exists
        Assessment assessment = assessmentRepository
                .findByName("COMMON_QUESTIONS")
                .orElseGet(() -> {
                    Assessment a = new Assessment();
                    a.setName("COMMON_QUESTIONS");
                    a.setDescription("Common questions applicable to all categories");
                    a.setStatus(AssessmentStatus.DRAFT);
                    a.setCreatedAt(LocalDateTime.now());
                    return assessmentRepository.save(a);
                });

        // Seed basic questions
        seedQuestionIfNotExists(
                assessment,
                "Full Name",
                QuestionType.TEXT,
                true,
                1,
                Map.of("minLength", 2, "maxLength", 100)
        );

        seedQuestionIfNotExists(
                assessment,
                "Gender",
                QuestionType.SINGLE_CHOICE,
                true,
                2,
                Map.of("allowedValues", new String[]{"Male", "Female", "Other"})
        );

        seedQuestionIfNotExists(
                assessment,
                "Age",
                QuestionType.TEXT,
                true,
                3,
                Map.of("min", 0, "max", 120)
        );

        seedQuestionIfNotExists(
                assessment,
                "Location",
                QuestionType.TEXT,
                false,
                4,
                null
        );

        seedQuestionIfNotExists(
                assessment,
                "Do you have health insurance?",
                QuestionType.SINGLE_CHOICE,
                false,
                5,
                Map.of("allowedValues", new String[]{"Yes", "No"})
        );
    }

    private void seedQuestionIfNotExists(
            Assessment assessment,
            String questionText,
            QuestionType questionType,
            boolean required,
            int orderIndex,
            Map<String, Object> config
    ) {

        if (questionRepository
                .existsByAssessmentIdAndQuestionText(
                        assessment.getId(), questionText)) {

            System.out.println("Question already exists: " + questionText);
            return;
        }

        AssessmentQuestion question = new AssessmentQuestion();
        question.setAssessment(assessment);
        question.setQuestionText(questionText);
        question.setQuestionType(questionType);
        question.setIsRequired(required);
        question.setOrderIndex(orderIndex);
        question.setConfig(config);
        question.setIsActive(true);
        question.setCreatedAt(LocalDateTime.now());

        questionRepository.save(question);
        System.out.println("Seeded question: " + questionText);
    }
}