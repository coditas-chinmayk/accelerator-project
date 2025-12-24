package com.example.accelerator.service;

import com.example.accelerator.dto.QuestionRequestDto;
import com.example.accelerator.dto.QuestionResponseDto;

import java.util.List;

public interface QuestionService {

    QuestionResponseDto createQuestion(QuestionRequestDto dto);

    QuestionResponseDto updateQuestion(Long questionId, QuestionRequestDto dto);

    void deleteQuestion(Long questionId);

    List<QuestionResponseDto> getAllQuestionsByAssessment(Long assessmentId);

    QuestionResponseDto getQuestionById(Long questionId);
}
