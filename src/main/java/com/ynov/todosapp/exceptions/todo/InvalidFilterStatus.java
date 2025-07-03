package com.ynov.todosapp.exceptions.todo;

public class InvalidFilterStatus extends  RuntimeException {
    public InvalidFilterStatus() {
        super("Invalid filter status");
    }
}
