package com.example.accelerator.controller.V1;

import com.example.accelerator.dto.ApiResponseDto;
import com.example.accelerator.dto.AssessmentCreateRequestDto;
import com.example.accelerator.dto.AssessmentDetailResponseDto;
import com.example.accelerator.dto.AssessmentListItemDto;
import com.example.accelerator.dto.AssessmentUpdateRequestDto;
import com.example.accelerator.service.AssessmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assessments")
@Tag(
        name = "Assessment Management",
        description = "APIs for managing assessments (create, read, update)"
)
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;

    @PostMapping
    @Operation(
            summary = "Create a new assessment",
            description = "Creates a new assessment in DRAFT status with name, description, and category"
    )
    public ApiResponseDto<AssessmentDetailResponseDto> createAssessment(
            @Valid @RequestBody AssessmentCreateRequestDto request) {

        AssessmentDetailResponseDto response = assessmentService.createAssessment(request);
        return ApiResponseDto.ok("Assessment created successfully", response);
    }

    @GetMapping
    @Operation(
            summary = "Get all assessments",
            description = "Retrieves a list of all assessments with basic details (id, name, category, status, etc.)"
    )
    public ApiResponseDto<List<AssessmentListItemDto>> getAllAssessments() {
        List<AssessmentListItemDto> assessments = assessmentService.getAllAssessments();
        return ApiResponseDto.ok("Assessments retrieved successfully", assessments);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get assessment by ID",
            description = "Fetches detailed information of a single assessment including its questions"
    )
    public ApiResponseDto<AssessmentDetailResponseDto> getAssessmentById(@PathVariable Long id) {
        AssessmentDetailResponseDto response = assessmentService.getAssessmentById(id);
        return ApiResponseDto.ok("Assessment retrieved successfully", response);
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update an existing assessment",
            description = "Partially updates assessment details (name, description, category). Only provided fields are updated."
    )
    public ApiResponseDto<AssessmentDetailResponseDto> updateAssessment(
            @PathVariable Long id,
            @Valid @RequestBody AssessmentUpdateRequestDto request) {

        AssessmentDetailResponseDto response = assessmentService.updateAssessment(id, request);
        return ApiResponseDto.ok("Assessment updated successfully", response);
    }
}