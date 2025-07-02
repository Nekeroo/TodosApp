package com.ynov.todosapp.utils;

import com.ynov.todosapp.exceptions.user.NameIsRequired;

public class UserValidator {

    public static void validateUsername(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NameIsRequired();
        }
    }

}
