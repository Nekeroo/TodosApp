package com.ynov.todosapp.enums;

public enum StatusEnum {

    TODO(0, "todo"),
    IN_PROGRESS(1, "progress"),
    DONE(2, "done");

    private int id;

    private String label;

    StatusEnum(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public static StatusEnum getStatusByString(String label) {
        for (StatusEnum status : StatusEnum.values()) {
            if (status.label.equalsIgnoreCase(label)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum constant with label " + label);
    }
}
