package com.example.accelerator.dto;

import lombok.Data;

import java.util.List;

@Data
public class PreviewResponseDto {
    private Long assessmentId;
    private String assessmentName;
    private List<PreviewQuestionDto> questions;
}
