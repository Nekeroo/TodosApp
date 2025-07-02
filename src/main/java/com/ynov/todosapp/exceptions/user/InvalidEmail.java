package com.ynov.todosapp.exceptions.user;

public class InvalidEmail extends RuntimeException {
    public InvalidEmail() {
        super("Invalid email format");
    }
}
