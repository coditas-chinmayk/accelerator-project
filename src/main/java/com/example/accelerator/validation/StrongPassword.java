package com.example.accelerator.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StrongPasswordValidator.class)
public @interface StrongPassword {

    String message() default
            "Password must be at least 8 characters long and contain uppercase, lowercase, number, and special character";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
