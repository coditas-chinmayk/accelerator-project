package com.example.accelerator.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentUpdateRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    private String category;
}