package com.example.accelerator.dto;

import lombok.Data;

import java.util.List;

@Data
public class PreviewQuestionDto {
    private Long questionId;
    private String questionText;
    private String questionType;
    private boolean required;
    private int order;
    private boolean visible;
    private List<String> options;
}
