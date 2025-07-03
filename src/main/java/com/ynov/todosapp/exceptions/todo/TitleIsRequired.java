package com.ynov.todosapp.exceptions.todo;

public class TitleIsRequired extends RuntimeException {
    public TitleIsRequired() {
        super("Title is required");
    }
}
