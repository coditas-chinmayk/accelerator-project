package com.example.accelerator.service;

import com.example.accelerator.domain.entity.User;
import com.example.accelerator.dto.LoggedInUserResponseDto;
import com.example.accelerator.exception.ResourceNotFoundException;
import com.example.accelerator.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public LoggedInUserResponseDto getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // from Basic Auth
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Logged-in user not found"));
        return LoggedInUserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                //.hospitalId(user.getHospital().getId())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}

