package com.example.accelerator.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// AssessmentListItemDto.java - for dashboard
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssessmentListItemDto {
    private Long id;
    private String name;
    private String category;        // or Category name as String
    private String status;          // "DRAFT"
    private LocalDateTime updatedAt;
    private String createdByName;   // optional
}
