package com.example.accelerator.dto;

import com.example.accelerator.domain.entity.AssessmentQuestion;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssessmentDetailResponseDto {
    private Long id;
    private Long userId;
    private String username;
    private String description;
    private String assessmentCategory;
    private String status;    //assessment status -> DRAFT or FINAL
    private List<AssessmentQuestion> assessmentQuestions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
