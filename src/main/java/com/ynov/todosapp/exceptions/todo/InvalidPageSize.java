package com.ynov.todosapp.exceptions.todo;

public class InvalidPageSize extends RuntimeException {
    public InvalidPageSize() {
        super("Invalid page size");
    }
}
