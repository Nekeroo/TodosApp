package com.ynov.todosapp.enums;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum TodoSort {
    TITLE("title"),
    CREATED_DATE("createdDate"),
    STATUS("status"),
    PRIORITY("priority");

    private final String label;

    TodoSort(String value) {
        this.label = value;
    }

    public static Optional<TodoSort> getSortByString(String label) {
        for (TodoSort sort : TodoSort.values()) {
            if (sort.label.equalsIgnoreCase(label)) {
                return Optional.of(sort);
            }
        }

        return Optional.empty();
    }
}
