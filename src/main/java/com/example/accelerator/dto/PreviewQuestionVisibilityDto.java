package com.example.accelerator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PreviewQuestionVisibilityDto {
    private Long questionId;
    private boolean visible;
}
