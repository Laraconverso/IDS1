package com.is1g6.backend.exceptions;

public class ValidationFailedException extends RuntimeException{
    public ValidationFailedException(String message) {
        super(message);
    }
}