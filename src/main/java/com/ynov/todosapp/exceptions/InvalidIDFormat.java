package com.ynov.todosapp.exceptions;

public class InvalidIDFormat extends  RuntimeException {
    public InvalidIDFormat() {
        super("Invalid ID format");
    }
}
