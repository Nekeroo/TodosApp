package com.ynov.todosapp.exceptions.user;

public class UserNotFound extends RuntimeException {
    public UserNotFound() {
        super("User not found");
    }
}
