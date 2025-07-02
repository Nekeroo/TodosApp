package com.ynov.todosapp.exceptions.user;

public class NameIsTooLong extends RuntimeException {
    public NameIsTooLong() {
        super("Name cannot exceed 50 characters");
    }
}
