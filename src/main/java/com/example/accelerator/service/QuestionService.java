package com.example.accelerator.service;

import com.example.accelerator.dto.QuestionRequestDTO;
import com.example.accelerator.dto.QuestionResponseDTO;

import java.util.List;

public interface QuestionService {

    QuestionResponseDTO createQuestion(QuestionRequestDTO dto);

    QuestionResponseDTO updateQuestion(Long questionId, QuestionRequestDTO dto);

    void deleteQuestion(Long questionId);

    List<QuestionResponseDTO> getAllQuestionsByAssessment(Long assessmentId);

    QuestionResponseDTO getQuestionById(Long questionId);
}
