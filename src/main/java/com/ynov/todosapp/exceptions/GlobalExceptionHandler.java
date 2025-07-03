package com.ynov.todosapp.exceptions;

import com.ynov.todosapp.exceptions.priority.InvalidPriorityException;
import com.ynov.todosapp.exceptions.todo.*;
import com.ynov.todosapp.exceptions.user.EmailAlreadyTaken;
import com.ynov.todosapp.exceptions.user.InvalidEmail;
import com.ynov.todosapp.exceptions.user.NameIsRequired;
import com.ynov.todosapp.exceptions.user.NameIsTooLong;
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

    @ExceptionHandler(InvalidPageSize.class)
    public ResponseEntity<String> handleInvalidPageSize() {
        return ResponseEntity.badRequest().body("Invalid page size");
    }

    @ExceptionHandler(InvalidFilterStatus.class)
    public ResponseEntity<String> handleInvalidFilterStatus() {
        return ResponseEntity.badRequest().body("Invalid filter status");
    }

    @ExceptionHandler(InvalidSortCriteria.class)
    public ResponseEntity<String> handleInvalidSortCriteria() {
        return ResponseEntity.badRequest().body("Invalid sort criteria");
    }

    @ExceptionHandler(InvalidEmail.class)
    public ResponseEntity<String> handleInvalidEmail() {
        return ResponseEntity.badRequest().body("Invalid email format");
    }

    @ExceptionHandler(NameIsTooLong.class)
    public ResponseEntity<String> handleNameTooLong() {
        return ResponseEntity.badRequest().body("Name cannot exceed 50 characters");
    }

    @ExceptionHandler(NameIsRequired.class)
    public ResponseEntity<String> handleNameIsRequired() {
        return ResponseEntity.badRequest().body("Name is required");
    }

    @ExceptionHandler(EmailAlreadyTaken.class)
    public ResponseEntity<String> handleEmailAlreadyTaken() {
        return ResponseEntity.badRequest().body("Email already in use");
    }

    @ExceptionHandler(InvalidPriorityException.class)
    public ResponseEntity<String> handleUserNotFound() {
        return ResponseEntity.badRequest().body("Invalid priority. Allowed values: LOW, NORMAL, HIGH, CRITICAL");
    }

    @ExceptionHandler(InvalidDeadlineFormat.class)
    public ResponseEntity<String> handleInvalidDeadlineFormat() {
        return ResponseEntity.badRequest().body("Invalid date format");
    }

}
