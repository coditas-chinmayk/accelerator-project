package com.example.accelerator.controller.V1;


import com.example.accelerator.dto.ApiResponseDto;
import com.example.accelerator.dto.CreateQuestionConditionRequestDto;
import com.example.accelerator.dto.QuestionConditionResponseDto;
import com.example.accelerator.dto.UpdateQuestionConditionRequestDto;
import com.example.accelerator.service.QuestionConditionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/assessments/{assessmentId}/questions/{questionId}/condition")
public class QuestionConditionController {

    @Autowired
    private QuestionConditionService questionConditionService;

    // CREATE conditional logic
    @PostMapping
    public ResponseEntity<ApiResponseDto<QuestionConditionResponseDto>> createCondition(
            @PathVariable Long questionId,
            @Valid @RequestBody CreateQuestionConditionRequestDto dto) {
        QuestionConditionResponseDto response = questionConditionService.createCondition(questionId, dto);
        return ResponseEntity.status(201).body(ApiResponseDto.ok("Conditional rule created successfully", response));
    }

    // UPDATE conditional logic
    @PutMapping
    public ResponseEntity<ApiResponseDto<QuestionConditionResponseDto>> updateCondition(
            @PathVariable Long questionId,
            @Valid @RequestBody UpdateQuestionConditionRequestDto dto) {
        QuestionConditionResponseDto response = questionConditionService.updateCondition(questionId, dto);
        return ResponseEntity.ok(ApiResponseDto.ok("Conditional rule updated successfully", response));
    }

    // DELETE conditional logic
    @DeleteMapping
    public ResponseEntity<ApiResponseDto<Void>> deleteCondition(
            @PathVariable Long assessmentId,
            @PathVariable Long questionId) {
        questionConditionService.deleteCondition(assessmentId, questionId);
        return ResponseEntity.ok(ApiResponseDto.ok("Conditional rule deleted successfully", null));
    }
}

