package com.ynov.todosapp.exceptions.user;

public class WrongCredentials extends RuntimeException {
    public WrongCredentials() {
        super("Email or password incorrect");
    }
}
