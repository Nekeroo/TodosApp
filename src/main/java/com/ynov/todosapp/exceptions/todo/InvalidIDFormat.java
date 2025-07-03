package com.ynov.todosapp.exceptions.todo;

public class InvalidIDFormat extends  RuntimeException {
    public InvalidIDFormat() {
        super("Invalid ID format");
    }
}
