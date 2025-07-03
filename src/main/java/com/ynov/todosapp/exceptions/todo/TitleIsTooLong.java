package com.ynov.todosapp.exceptions.todo;

public class TitleIsTooLong extends  RuntimeException {
    public TitleIsTooLong() {
        super("Title cannot exceed 100 characters");
    }
}
