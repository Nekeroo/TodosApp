package com.ynov.todosapp.exceptions.todo;

public class TaskNotFound extends RuntimeException {
    public TaskNotFound() {
        super("Task not found");
    }
}
