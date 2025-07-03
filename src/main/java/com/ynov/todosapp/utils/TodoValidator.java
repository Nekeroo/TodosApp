package com.ynov.todosapp.utils;

import com.ynov.todosapp.enums.PriorityEnum;
import com.ynov.todosapp.exceptions.priority.InvalidPriorityException;
import com.ynov.todosapp.exceptions.todo.DescriptionIsTooLong;
import com.ynov.todosapp.exceptions.todo.TitleIsRequired;
import com.ynov.todosapp.exceptions.todo.TitleIsTooLong;

public class TodoValidator {
    public static void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new TitleIsRequired();
        }

        if (title.trim().length() > 100) {
            throw new TitleIsTooLong();
        }
    }

    public static void validateDescription(String description) {
        if (description != null && description.trim().length() > 500) {
            throw new DescriptionIsTooLong();
        }
    }

    public static void validatePriority(String priority) {
        if (priority != null && !priority.isEmpty() && PriorityEnum.fromString(priority).isEmpty()) {
            throw new InvalidPriorityException();
        }

    }
}
