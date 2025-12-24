package com.example.accelerator.service;

import com.example.accelerator.domain.entity.Assessment;
import com.example.accelerator.domain.entity.AssessmentQuestion;
import com.example.accelerator.dto.QuestionRequestDto;
import com.example.accelerator.dto.QuestionResponseDto;
import com.example.accelerator.exception.ResourceNotFoundException;
import com.example.accelerator.exception.BadRequestException;
import com.example.accelerator.repository.AssessmentQuestionRepository;
import com.example.accelerator.repository.AssessmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private AssessmentQuestionRepository questionRepository;


    @Override
    public QuestionResponseDto createQuestion(QuestionRequestDto dto) {

        validateQuestionRequest(dto);
        Assessment assessment=assessmentRepository.findById(dto.getAssessmentId())
                .orElseThrow(()-> new ResourceNotFoundException("Assessment Not found with this id"));


        if (questionRepository.existsByAssessmentIdAndOrderIndex(
                assessment.getId(), dto.getOrderIndex())) {
            throw new BadRequestException("Order index already exists for this assessment");
        }

        AssessmentQuestion question = AssessmentQuestion.builder()
                .assessment(assessment)
                .questionText(dto.getQuestionText())
                .questionType(dto.getQuestionType())
                .isRequired(dto.getIsRequired())
                .orderIndex(dto.getOrderIndex())
                .config(dto.getConfig())
                .isActive(true)
                .build();

        AssessmentQuestion saved = questionRepository.save(question);

        return mapToResponse(saved);
    }



    @Override
    public QuestionResponseDto updateQuestion(Long questionId, QuestionRequestDto dto) {
        validateQuestionRequest(dto);

        AssessmentQuestion question = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Question not found with id " + questionId));

        if (!question.getAssessment().getId().equals(dto.getAssessmentId())) {
            throw new BadRequestException("Assessment ID mismatch for question update");
        }

        question.setQuestionText(dto.getQuestionText());
        question.setQuestionType(dto.getQuestionType());
        question.setIsRequired(dto.getIsRequired());
        question.setConfig(dto.getConfig());

        if (!question.getOrderIndex().equals(dto.getOrderIndex())) {

            boolean orderExists = questionRepository
                    .existsByAssessmentIdAndOrderIndex(
                            question.getAssessment().getId(), dto.getOrderIndex());

            if (orderExists) {
                throw new BadRequestException("Order index already exists for this assessment");
            }

            question.setOrderIndex(dto.getOrderIndex());
        }

        return mapToResponse(question);
    }



    @Override
    public void deleteQuestion(Long questionId) {
        AssessmentQuestion question = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Question not found with id " + questionId));

        question.setIsActive(false);
    }



    @Override
    public List<QuestionResponseDto> getAllQuestionsByAssessment(Long assessmentId) {

        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Assessment not found with id " + assessmentId));

        return questionRepository
                .findByAssessmentIdAndIsActiveTrueOrderByOrderIndexAsc(assessment.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }



    @Override
    public QuestionResponseDto getQuestionById(Long questionId) {

        AssessmentQuestion question = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Question not found with id " + questionId));

        if (!Boolean.TRUE.equals(question.getIsActive())) {
            throw new ResourceNotFoundException("Question is inactive");
        }

        return mapToResponse(question);
    }



    // FOR VALIDATE question request :
    private void validateQuestionRequest(QuestionRequestDto dto) {

        if (dto.getQuestionText() == null || dto.getQuestionText().isBlank()) {
            throw new BadRequestException("Question text cannot be empty");
        }

        if (dto.getOrderIndex() <= 0) {
            throw new BadRequestException("Order index must be greater than zero");
        }

        if (dto.getQuestionType() == null) {
            throw new BadRequestException("Question type is required");
        }
    }




    // Object Mapper :
    private QuestionResponseDto mapToResponse(AssessmentQuestion question) {

        return QuestionResponseDto.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .questionType(question.getQuestionType())
                .isRequired(question.getIsRequired())
                .orderIndex(question.getOrderIndex())
                .config(question.getConfig())
                .isActive(question.getIsActive())
                .createdAt(question.getCreatedAt())
                .updatedAt(question.getUpdatedAt())
                .build();
    }
}
