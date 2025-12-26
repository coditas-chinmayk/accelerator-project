package com.example.accelerator.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LoggedInUserResponseDto {

    private Long id;
    private String name;
    private String email;
    //private Long hospitalId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
