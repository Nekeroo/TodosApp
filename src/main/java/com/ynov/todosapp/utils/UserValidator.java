package com.ynov.todosapp.utils;

import com.ynov.todosapp.exceptions.user.InvalidEmail;
import com.ynov.todosapp.exceptions.user.NameIsRequired;
import com.ynov.todosapp.exceptions.user.NameIsTooLong;

public class UserValidator {

    public static void validateUsername(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NameIsRequired();
        }

        if (name.length() > 50) {
            throw new NameIsTooLong();
        }
    }

    public static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new InvalidEmail();
        }
    }
}
