package com.ynov.todosapp.utils;

import com.ynov.todosapp.exceptions.DescriptionIsTooLong;
import com.ynov.todosapp.exceptions.TitleIsRequired;

public class TodoValidator {
    public static void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new TitleIsRequired();
        }

        if (title.trim().length() > 100) {
            throw new TitleIsRequired();
        }
    }

    public static void validateDescription(String description) {
        if (description.trim().length() > 500) {
            throw new DescriptionIsTooLong();
        }
    }
}
