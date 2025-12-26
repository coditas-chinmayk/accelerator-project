package com.example.accelerator.controller.V1;

import com.example.accelerator.dto.ApiResponseDto;
import com.example.accelerator.dto.PreviewEvaluationRequestDto;
import com.example.accelerator.dto.PreviewEvaluationResponseDto;
import com.example.accelerator.dto.PreviewResponseDto;
import com.example.accelerator.service.PreviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assessments")
@RequiredArgsConstructor
@Tag(
        name = "Preview Module",
        description = "APIs for preview the assessment and interaction")
public class PreviewController {

    private final PreviewService previewService;

    @GetMapping("/{assessmentId}/preview")
    @Operation(
            summary = "preview the assignment by ID")
    public ResponseEntity<ApiResponseDto<PreviewResponseDto>> getAssessmentPreview(
            @PathVariable Long assessmentId) {

        PreviewResponseDto previewResponse = previewService.getPreview(assessmentId);

        ApiResponseDto<PreviewResponseDto> response =
                ApiResponseDto.ok(
                        "Assessment preview fetched successfully",
                        previewResponse
                );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{assessmentId}/preview/evaluate")
    @Operation(
            summary = "preview the interaction",
            description = "admin can check how thw assessment will look after interacting with it")
    public ResponseEntity<ApiResponseDto<PreviewEvaluationResponseDto>> evaluatePreview(
            @PathVariable Long assessmentId,
            @RequestBody PreviewEvaluationRequestDto request
    ) {
        PreviewEvaluationResponseDto res = previewService.evaluate(assessmentId, request);
        return ResponseEntity.ok(ApiResponseDto.ok("Evaluation complete", res));
    }
}
