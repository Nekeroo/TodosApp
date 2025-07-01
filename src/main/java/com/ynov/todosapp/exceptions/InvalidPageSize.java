package com.ynov.todosapp.exceptions;

public class InvalidPageSize extends RuntimeException {
    public InvalidPageSize() {
        super("Invalid page size");
    }
}
