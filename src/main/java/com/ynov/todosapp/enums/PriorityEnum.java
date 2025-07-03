package com.ynov.todosapp.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public enum PriorityEnum {

    LOW(0, "low"),
    NORMAL(1, "normal"),
    HIGH(2, "high"),
    MAJOR(3, "major");

    private int id;

    private String label;

    public static Optional<PriorityEnum> fromString(String label) {

        for (PriorityEnum priority : PriorityEnum.values()) {
            if (priority.label.equalsIgnoreCase(label)) {
                return Optional.of(priority);
            }
        }

        return Optional.empty();

    }

}
