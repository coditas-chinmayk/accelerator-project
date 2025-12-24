package com.example.accelerator.dto;

import com.example.accelerator.domain.enums.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionRequestDto {

    @NotNull(message = "Assessment id is required")
    private Long assessmentId;

    @NotBlank(message = "Question text cannot be empty")
    private String questionText;

    @NotNull(message = "Question type is required")
    private QuestionType questionType;

    // optional in entity, so optional here
    private Boolean isRequired;

    @NotNull(message = "Order index is required")
    @Positive(message = "Order index must be positive")
    private Integer orderIndex;

    // JSONB config – optional
    private Map<String, Object> config;

}
