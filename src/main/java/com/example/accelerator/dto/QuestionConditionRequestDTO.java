package com.example.accelerator.dto;

import com.example.accelerator.domain.enums.ConditionOperator;
import com.example.accelerator.domain.enums.ExpectedValue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionConditionRequestDTO {

    @NotNull(message = "Question id is required")
    private Long questionId;

    @NotNull(message = "Depends-on question id is required")
    private Long dependsOnQuestionId;

    @NotNull(message = "Condition operator is required")
    private ConditionOperator operator;

    @NotNull(message = "Expected value is required")
    private ExpectedValue expectedValue;
}
