package com.example.demo.exception;

public class BadRequestRuntimeException extends RuntimeException {
    public BadRequestRuntimeException(String message) {
        super(message);
    }
}
