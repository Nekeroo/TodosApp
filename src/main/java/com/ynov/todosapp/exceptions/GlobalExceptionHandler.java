package com.ynov.todosapp.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFound.class)
    public ResponseEntity<String> handleTaskNotFound() {
        return ResponseEntity.status(404).body("Task not found");
    }

    @ExceptionHandler(InvalidIDFormat.class)
    public ResponseEntity<String> handleInvalidIDFormat() {
        return ResponseEntity.badRequest().body("Invalid ID format");
    }

    @ExceptionHandler(TitleIsRequired.class)
    public ResponseEntity<String> handleTitleIsRequired() {
        return ResponseEntity.badRequest().body("Title is required");
    }

    @ExceptionHandler(TitleIsTooLong.class)
    public ResponseEntity<String> handleTitleTooLong() {
        return ResponseEntity.badRequest().body("Title cannot exceed 100 characters");
    }

    @ExceptionHandler(DescriptionIsTooLong.class)
    public ResponseEntity<String> handleDescriptionTooLong() {
        return ResponseEntity.badRequest().body("Description cannot exceed 500 characters");
    }

    @ExceptionHandler(InvalidStatus.class)
    public ResponseEntity<String> handleInvalidStatus() {
        return ResponseEntity.badRequest().body("Invalid status. Allowed values: TODO, ONGOING, DONE");
    }
}
