package com.example.accelerator.service;

import com.example.accelerator.domain.entity.AssessmentQuestion;
import com.example.accelerator.domain.entity.QuestionCondition;
import com.example.accelerator.dto.CreateQuestionConditionRequestDto;
import com.example.accelerator.dto.QuestionConditionResponseDto;
import com.example.accelerator.repository.AssessmentQuestionRepository;
import com.example.accelerator.repository.QuestionConditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class QuestionConditionService {

    private final QuestionConditionRepository conditionRepository;
    private final AssessmentQuestionRepository questionRepository;

    public QuestionConditionResponseDto createCondition(Long questionId, CreateQuestionConditionRequestDto dto) {

        //  Validate child question
        AssessmentQuestion childQuestion = questionRepository.findById(questionId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Question not found"
                        ));

        // Validate parent question
        AssessmentQuestion parentQuestion = questionRepository.findById(dto.getDependsOnQuestionId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Depends-on question not found"
                        ));

        //  Validate same assessment
        if (!childQuestion.getAssessment().getId().equals(parentQuestion.getAssessment().getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Questions must belong to the same assessment"
            );
        }

        // Validate previous question rule
        if (parentQuestion.getOrderIndex() >= childQuestion.getOrderIndex()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Conditional rule must reference a previous question"
            );
        }

        // Validate one rule per question
        if (conditionRepository.existsByQuestionId(questionId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Conditional rule already exists for this question"
            );
        }

        // Persist
        QuestionCondition condition = QuestionCondition.builder()
                .question(childQuestion)
                .dependsOnQuestion(parentQuestion)
                .operator(dto.getOperator())
                .expectedValue(dto.getExpectedValue())
                .build();

        QuestionCondition savedCondition = conditionRepository.save(condition);
        return mapToResponse(savedCondition);
    }

    // Mapping belongs in service
    private QuestionConditionResponseDto mapToResponse(QuestionCondition condition) {
        return QuestionConditionResponseDto.builder()
                .id(condition.getId())
                .questionId(condition.getQuestion().getId())
                .dependsOnQuestionId(condition.getDependsOnQuestion().getId())
                .operator(condition.getOperator())
                .expectedValue(condition.getExpectedValue())
                .build();
    }
}

