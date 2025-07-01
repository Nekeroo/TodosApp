package com.ynov.todosapp.exceptions;

public class DescriptionIsTooLong extends  RuntimeException {
    public DescriptionIsTooLong() {
        super("Description cannot exceed 500 characters");
    }
}
