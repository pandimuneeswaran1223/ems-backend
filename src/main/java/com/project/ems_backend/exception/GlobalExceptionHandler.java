package com.project.ems_backend.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Duplicate email, username handle pannrom
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateEntry(
            DataIntegrityViolationException ex) {
        Map<String, String> error = new HashMap<>();

        if (ex.getMessage().contains("email")) {
            error.put("message", "Email already exists!");
        } else if (ex.getMessage().contains("user_name")) {
            error.put("message", "Username already exists!");
        } else {
            error.put("message", "Duplicate entry found!");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error); // 409 Conflict
    }

    // Runtime exceptions handle pannrom
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(
            RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error); // 400
    }

    // Resource not found handle pannrom
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(
            ResourceNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error); // 404
    }
}