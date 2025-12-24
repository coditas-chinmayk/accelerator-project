package com.example.accelerator.dto;

import com.example.accelerator.domain.enums.ConditionOperator;
import com.example.accelerator.domain.enums.ExpectedValue;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionConditionResponseDTO {

    private Long id;
    private Long questionId;
    private Long dependsOnQuestionId;
    private ConditionOperator operator;
    private ExpectedValue expectedValue;
}

