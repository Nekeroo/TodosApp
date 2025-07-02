package com.ynov.todosapp.exceptions.user;

public class NameIsRequired extends RuntimeException {
    public NameIsRequired() {
        super("Name is required");
    }
}
