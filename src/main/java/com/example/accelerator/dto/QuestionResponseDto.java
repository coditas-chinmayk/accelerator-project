package com.example.accelerator.dto;

import com.example.accelerator.domain.enums.QuestionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)  // any field which goes null doesn't appear as in Json
public class QuestionResponseDto {

    private Long id;
    private String questionText;
    private QuestionType questionType;
    private Boolean isRequired;
    private Integer orderIndex;

    private Map<String, Object> config;

    private Boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
