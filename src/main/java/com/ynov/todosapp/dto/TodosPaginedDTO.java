package com.ynov.todosapp.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodosPaginedDTO {

    private Iterable<TodoDTO> todos;
    private int totalItems;
    private int totalPages;
    private int currentPage;
}
