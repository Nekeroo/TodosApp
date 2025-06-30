package com.ynov.todosapp.dto.input;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TodoInputDTO {

    private String title;

    private String description;

}
