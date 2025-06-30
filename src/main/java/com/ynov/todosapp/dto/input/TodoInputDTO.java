package com.ynov.todosapp.dto.input;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoInputDTO {

    private String title;

    private String description;

}
