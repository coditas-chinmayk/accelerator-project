package com.example.accelerator.dto;

import com.example.accelerator.domain.entity.AssessmentQuestion;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssessmentResponseDto {
    private Long id;
    private Long userId;
    private String username;
    private String assessmentCategory;
    private List<AssessmentQuestion> assessmentQuestions;



}
