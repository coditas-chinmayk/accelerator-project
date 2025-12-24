package com.example.accelerator.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleResourceAlreadyExistsException(
            ResourceAlreadyExistsException ex) {

        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyExistException(
            EmailAlreadyExistException ex) {

        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(
            ResourceNotFoundException ex) {

        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> BadRequestException(
            BadRequestException ex) {

        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(
            UserNotFoundException ex) {

        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidQuestionTypeException.class)
    public ResponseEntity<ApiError> handleInvalidQuestionTypeException(
            InvalidQuestionTypeException ex) {

        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(
            ConstraintViolationException ex) {

        //  NOT exposing DB/Hibernate message
        ApiError apiError = new ApiError(
                "Invalid request data. Please check your input.",
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex) {

        ApiError apiError = new ApiError(
                "Something went wrong. Please try again later.",
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ApiError> handleAccessDeniedException(
//            AccessDeniedException ex) {
//
//        ApiError apiError = new ApiError("Access denied", HttpStatus.FORBIDDEN);
//        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
//    }
}
