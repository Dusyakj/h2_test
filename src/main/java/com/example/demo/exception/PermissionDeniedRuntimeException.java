package com.example.demo.exception;

public class PermissionDeniedRuntimeException extends RuntimeException {
    public PermissionDeniedRuntimeException(String message) {
        super(message);
    }
}
