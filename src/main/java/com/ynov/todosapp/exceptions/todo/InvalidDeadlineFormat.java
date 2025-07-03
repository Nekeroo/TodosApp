package com.ynov.todosapp.exceptions.todo;

public class InvalidDeadlineFormat extends RuntimeException {
    public InvalidDeadlineFormat() {
        super("Invalid date format");
    }
}
