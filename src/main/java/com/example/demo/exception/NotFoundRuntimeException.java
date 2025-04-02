package com.example.demo.exception;

public class NotFoundRuntimeException extends RuntimeException {
    public NotFoundRuntimeException(String message) {
        super(message);
    }
}
