package com.example.accelerator.exception;

public class EmailAlreadyExistException extends RuntimeException{
    public EmailAlreadyExistException(String userAlreadyExists) {
        super(userAlreadyExists);
    }
}
