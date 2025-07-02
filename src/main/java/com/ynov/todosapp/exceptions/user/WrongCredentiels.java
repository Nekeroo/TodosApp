package com.ynov.todosapp.exceptions.user;

public class WrongCredentiels extends RuntimeException {
    public WrongCredentiels() {
        super("Email or password incorrect");
    }
}
