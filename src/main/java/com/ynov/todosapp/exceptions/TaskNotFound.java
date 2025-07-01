package com.ynov.todosapp.exceptions;

public class TaskNotFound extends RuntimeException {
    public TaskNotFound() {
        super("Task not found");
    }
}
