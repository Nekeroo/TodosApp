package com.ynov.todosapp.exceptions;

public class TitleIsRequired extends RuntimeException {
    public TitleIsRequired() {
        super("Title is required");
    }
}
