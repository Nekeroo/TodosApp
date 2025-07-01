package com.ynov.todosapp.exceptions;

public class TitleIsTooLong extends  RuntimeException {
    public TitleIsTooLong() {
        super("Title cannot exceed 100 characters");
    }
}
