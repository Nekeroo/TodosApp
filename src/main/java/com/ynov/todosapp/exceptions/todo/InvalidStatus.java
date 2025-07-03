package com.ynov.todosapp.exceptions.todo;

public class InvalidStatus extends  RuntimeException {
    public  InvalidStatus() {
        super("Invalid status. Allowed values: TODO, ONGOING, DONE");
    }
}
