package com.ynov.todosapp.exceptions.priority;

public class InvalidPriorityException extends RuntimeException {
    public InvalidPriorityException() {
        super("Invalid priority. Allowed values: LOW, NORMAL, HIGH, CRITICAL");
    }
}
