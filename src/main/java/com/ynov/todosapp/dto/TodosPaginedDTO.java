package com.ynov.todosapp.dto;

import lombok.*;

import java.util.Collection;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodosPaginedDTO {

    private Collection<TodoDTO> todos;
    private int totalItems;
    private int totalPages;
    private int currentPage;
}
