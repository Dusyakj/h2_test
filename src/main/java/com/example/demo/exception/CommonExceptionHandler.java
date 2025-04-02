package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(NotFoundRuntimeException.class)
    public ResponseEntity<ErrorDto> handleNotFoundException(NotFoundRuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(404, e.getMessage()));
    }

    @ExceptionHandler(PermissionDeniedRuntimeException.class)
    public ResponseEntity<ErrorDto> handlePermissionDeniedException(PermissionDeniedRuntimeException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorDto(403, e.getMessage()));
    }

    @ExceptionHandler(BadRequestRuntimeException.class)
    public ResponseEntity<ErrorDto> handleBadRequestException(BadRequestRuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(400, e.getMessage()));
    }
}
