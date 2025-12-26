package com.example.accelerator.service;

import com.example.accelerator.dto.AssessmentCreateRequestDto;
import com.example.accelerator.dto.AssessmentDetailResponseDto;
import com.example.accelerator.dto.AssessmentListItemDto;
import com.example.accelerator.dto.AssessmentUpdateRequestDto;

import java.util.List;

public interface AssessmentService {
    AssessmentDetailResponseDto createAssessment(AssessmentCreateRequestDto request);
    List<AssessmentListItemDto> getAllAssessments();
    AssessmentDetailResponseDto getAssessmentById(Long id);
    AssessmentDetailResponseDto updateAssessment(Long id, AssessmentUpdateRequestDto request);

}