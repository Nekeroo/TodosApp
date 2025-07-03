package com.ynov.todosapp.exceptions.todo;

public class DescriptionIsTooLong extends  RuntimeException {
    public DescriptionIsTooLong() {
        super("Description cannot exceed 500 characters");
    }
}
