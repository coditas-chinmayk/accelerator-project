package com.example.accelerator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PreviewEvaluationResponseDto {
    private List<PreviewQuestionVisibilityDto> visibility;
}
