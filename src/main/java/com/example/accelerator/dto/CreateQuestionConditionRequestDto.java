package com.example.accelerator.dto;

import com.example.accelerator.domain.enums.ConditionOperator;
import com.example.accelerator.domain.enums.ExpectedValue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateQuestionConditionRequestDto {

    @NotNull(message = "Depends on question id is required")
    private Long dependsOnQuestionId;

    @NotNull(message = "Operator is required")
    private ConditionOperator operator;   // EQUALS

    @NotNull(message = "Expected value is required")
    private ExpectedValue expectedValue;  // YES / NO
}

