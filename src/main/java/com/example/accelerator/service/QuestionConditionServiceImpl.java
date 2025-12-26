package com.example.accelerator.service;

import com.example.accelerator.domain.entity.AssessmentQuestion;
import com.example.accelerator.domain.entity.QuestionCondition;
import com.example.accelerator.domain.enums.QuestionType;
import com.example.accelerator.dto.CreateQuestionConditionRequestDto;

import com.example.accelerator.dto.QuestionConditionResponseDto;
import com.example.accelerator.dto.UpdateQuestionConditionRequestDto;
import com.example.accelerator.exception.InvalidConditionalRuleException;
import com.example.accelerator.exception.ResourceAlreadyExistsException;
import com.example.accelerator.exception.ResourceNotFoundException;
import com.example.accelerator.repository.AssessmentQuestionRepository;
import com.example.accelerator.repository.QuestionConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionConditionServiceImpl implements QuestionConditionService{
    @Autowired
    private  QuestionConditionRepository conditionRepository;
    @Autowired
    private  AssessmentQuestionRepository questionRepository;

    public QuestionConditionResponseDto createCondition(Long questionId, CreateQuestionConditionRequestDto dto) {

        // Validate child question
        AssessmentQuestion childQuestion = questionRepository.findById(questionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

        // Validate parent question
        AssessmentQuestion parentQuestion = questionRepository.findById(dto.getDependsOnQuestionId())
                        .orElseThrow(() -> new ResourceNotFoundException("Depends-on question not found"));

        if (parentQuestion.getQuestionType() == QuestionType.TEXT) {
            throw new InvalidConditionalRuleException("Conditional logic is not supported for text questions");
        }

        // Validate same assessment
        if (!childQuestion.getAssessment().getId().equals(parentQuestion.getAssessment().getId())) {
            throw new InvalidConditionalRuleException("Questions must belong to the same assessment");
        }

        // Validate previous question rule
        if (parentQuestion.getOrderIndex() >= childQuestion.getOrderIndex()) {
            throw new InvalidConditionalRuleException("Conditional rule must reference a previous question");
        }

        //  Validate one rule per question
        if (conditionRepository.existsByQuestionId(questionId)) {
            throw new ResourceAlreadyExistsException("Conditional rule already exists for this question");
        }

        childQuestion.setIsActive(true);
        questionRepository.save(childQuestion);

        // Persist rule
        QuestionCondition condition = QuestionCondition.builder()
                .question(childQuestion)
                .dependsOnQuestion(parentQuestion)
                .operator(dto.getOperator())
                .expectedValue(dto.getExpectedValue())
                .build();

        QuestionCondition savedCondition = conditionRepository.save(condition);

        return mapToResponse(savedCondition);
    }


    public QuestionConditionResponseDto updateCondition(Long questionId, UpdateQuestionConditionRequestDto dto) {

        //  Ensure existing rule exists
        QuestionCondition existingCondition = conditionRepository.findByQuestion_Id(questionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Conditional rule does not exist for this question"));

        //  Delete existing rule
        conditionRepository.delete(existingCondition);

        //  Recreate rule using same validations as CREATE
        AssessmentQuestion childQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

        AssessmentQuestion parentQuestion = questionRepository.findById(dto.getDependsOnQuestionId())
                        .orElseThrow(() -> new ResourceNotFoundException("Depends-on question not found"));

        if (parentQuestion.getQuestionType() == QuestionType.TEXT) {
            throw new InvalidConditionalRuleException("Conditional logic is not supported for text questions");
        }

        if (!childQuestion.getAssessment().getId().equals(parentQuestion.getAssessment().getId())) {
            throw new InvalidConditionalRuleException("Questions must belong to the same assessment");
        }

        if (parentQuestion.getOrderIndex() >= childQuestion.getOrderIndex()) {
            throw new InvalidConditionalRuleException("Conditional rule must reference a previous question");
        }

        childQuestion.setIsActive(true);
        questionRepository.save(childQuestion);

        QuestionCondition newCondition = QuestionCondition.builder()
                .question(childQuestion)
                .dependsOnQuestion(parentQuestion)
                .operator(dto.getOperator())
                .expectedValue(dto.getExpectedValue())
                .build();

        QuestionCondition savedCondition = conditionRepository.save(newCondition);
        return mapToResponse(savedCondition);
    }

    public void deleteCondition(Long assessmentId, Long questionId) {

        AssessmentQuestion question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

        if (!question.getAssessment().getId().equals(assessmentId)) {
            throw new InvalidConditionalRuleException("Question does not belong to the given assessment");
        }

        QuestionCondition condition = conditionRepository.findByQuestion_Id(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Conditional rule does not exist for this question"));

        conditionRepository.delete(condition);
        question.setIsActive(false);
        questionRepository.save(question);
    }


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
