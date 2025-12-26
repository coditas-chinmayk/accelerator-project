package com.example.accelerator.controller.V1;

import com.example.accelerator.dto.ApiResponseDto;
import com.example.accelerator.dto.LoggedInUserResponseDto;
import com.example.accelerator.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponseDto<LoggedInUserResponseDto>> getLoggedInUser() {
        LoggedInUserResponseDto user = authService.getLoggedInUser();
        return ResponseEntity.ok(ApiResponseDto.ok("Logged-in admin fetched successfully", user));
    }
}
