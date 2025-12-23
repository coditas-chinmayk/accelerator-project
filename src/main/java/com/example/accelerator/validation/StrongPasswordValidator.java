package com.example.accelerator.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class StrongPasswordValidator
        implements ConstraintValidator<StrongPassword, String> {

    /***
     Rules for password :
     1. Minimum 8 characters
     2. At least 1 uppercase
     3. At least 1 lowercase
     4. At least 1 digit
     5. At least 1 special character
     6. Reject common weak passwords
     ***/


    private static final int MIN_LENGTH = 8;

    // for Common weak passwords :
    private static final Set<String> COMMON_PASSWORDS = Set.of(
            "password",
            "password123",
            "admin",
            "admin123",
            "qwerty",
            "123456",
            "12345678",
            "welcome",
            "letmein"
    );

    @Override
    public boolean isValid(String password,
                           ConstraintValidatorContext context) {

        if (password == null || password.trim().isEmpty()) {
            return false;
        }

        if (password.length() < MIN_LENGTH) {
            return false;
        }

        // Reject common passwords (case-insensitive)
        if (COMMON_PASSWORDS.contains(password.toLowerCase())) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char ch : password.toCharArray()) {

            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowercase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else {
                hasSpecialChar = true;
            }
        }

        return hasUppercase
                && hasLowercase
                && hasDigit
                && hasSpecialChar;
    }
}

