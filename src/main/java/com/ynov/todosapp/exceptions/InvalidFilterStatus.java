package com.ynov.todosapp.exceptions;

public class InvalidFilterStatus extends  RuntimeException {
    public InvalidFilterStatus() {
        super("Invalid filter status");
    }
}
