package com.example.accelerator.dto;

import lombok.Data;

import java.util.Map;

@Data
public class PreviewEvaluationRequestDto {
    // Key = QuestionId, Value = Answer (String OR List)
    private Map<Long, Object> answers;
}
