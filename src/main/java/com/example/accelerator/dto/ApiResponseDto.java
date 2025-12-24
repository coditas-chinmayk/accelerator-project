package com.example.accelerator.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDto<T> {
    private Boolean success;
    private String message;
    private T data;
    private HttpStatus status;
    private LocalDateTime timeStamp;

    public static <T> ApiResponseDto<T> ok(String message, T data){
        return ApiResponseDto.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .status(HttpStatus.OK)
                .timeStamp(LocalDateTime.now())
                .build();

    }

    public static <T> ApiResponseDto<T> error(String message, HttpStatus status){
        return ApiResponseDto.<T>builder()
                .success(false)
                .message(message)
                .status(status)
                .timeStamp(LocalDateTime.now())
                .build();
    }



}
