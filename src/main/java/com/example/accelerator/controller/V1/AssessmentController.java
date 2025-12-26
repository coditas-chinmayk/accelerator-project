package com.example.accelerator.controller.V1;

import com.example.accelerator.dto.ApiResponseDto;
import com.example.accelerator.dto.AssessmentCreateRequestDto;
import com.example.accelerator.dto.AssessmentUpdateRequestDto;
import com.example.accelerator.dto.AssessmentDetailResponseDto;
import com.example.accelerator.dto.AssessmentListItemDto;
import com.example.accelerator.service.AssessmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assessments")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;

    @PostMapping
    public ApiResponseDto<AssessmentDetailResponseDto> createAssessment(
            @Valid @RequestBody AssessmentCreateRequestDto request) {

        AssessmentDetailResponseDto response = assessmentService.createAssessment(request);
        return ApiResponseDto.ok("Assessment created successfully", response);
    }

    @GetMapping
    public ApiResponseDto<List<AssessmentListItemDto>> getAllAssessments() {
        List<AssessmentListItemDto> assessments = assessmentService.getAllAssessments();
        return ApiResponseDto.ok("Assessments retrieved successfully", assessments);
    }

    @GetMapping("/{id}")
    public ApiResponseDto<AssessmentDetailResponseDto> getAssessmentById(@PathVariable Long id) {
        AssessmentDetailResponseDto response = assessmentService.getAssessmentById(id);
        return ApiResponseDto.ok("Assessment retrieved successfully", response);
    }

    @PatchMapping("/{id}")
    public ApiResponseDto<AssessmentDetailResponseDto> updateAssessment(
            @PathVariable Long id,
            @Valid @RequestBody AssessmentUpdateRequestDto request) {

        AssessmentDetailResponseDto response = assessmentService.updateAssessment(id, request);
        return ApiResponseDto.ok("Assessment updated successfully", response);
    }
}