package com.ynov.todosapp.dto;

import com.ynov.todosapp.enums.StatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class TodoDTO {

    private long publicId;

    private long id;

    private String title;

    private String description;

    private String status;

    private LocalDate createdDate;

}
