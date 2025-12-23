package com.example.accelerator.exception;

public class ResourceAlreadyExistsException extends RuntimeException{
    public ResourceAlreadyExistsException(String resourceAlreadyExists) {
        super(resourceAlreadyExists);
    }
}
