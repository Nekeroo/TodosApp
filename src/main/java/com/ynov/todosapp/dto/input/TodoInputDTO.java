package com.ynov.todosapp.dto.input;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoInputDTO {

    private String title;

    private String description;

    private String priority;

    private LocalDate deadline;

}
