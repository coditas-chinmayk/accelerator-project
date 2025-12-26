package com.example.accelerator.service;

import com.example.accelerator.dto.CreateQuestionConditionRequestDto;
import com.example.accelerator.dto.QuestionConditionResponseDto;
import com.example.accelerator.dto.UpdateQuestionConditionRequestDto;

public interface QuestionConditionService {

    QuestionConditionResponseDto createCondition(Long questionId, CreateQuestionConditionRequestDto dto);

    QuestionConditionResponseDto updateCondition(Long questionId, UpdateQuestionConditionRequestDto dto);

    void deleteCondition(Long assessmentId, Long questionId);
}

