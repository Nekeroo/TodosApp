package com.ynov.todosapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class TodoDTO {

    private long id;

    private String title;

    private String description;

    private String status;

    private LocalDate createdDate;

    private String priority;

    private Long userAffected;

    @Override
    public String toString() {
        return "TodoDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
