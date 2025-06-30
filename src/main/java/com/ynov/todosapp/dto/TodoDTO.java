package com.ynov.todosapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TodoDTO {

    private String title;

    private String description;

    private boolean isDone;


}
