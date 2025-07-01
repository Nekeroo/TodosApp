package com.ynov.todosapp.mapper;

import com.ynov.todosapp.dto.TodoDTO;
import com.ynov.todosapp.dto.TodosPaginedDTO;
import com.ynov.todosapp.models.Todo;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class TodoMapper {
    public static TodoDTO todoToDTO(Todo todo) {
        return TodoDTO.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .createdDate(todo.getCreatedDate())
                .status(todo.getStatus().getLabel())
                .build();
    }

    public static TodosPaginedDTO todoPageToDTO(Page<Todo> todoPage) {
        List<TodoDTO> todos = new ArrayList<>(todoPage.getContent().stream()
                .map(TodoMapper::todoToDTO)
                .toList());

        return TodosPaginedDTO.builder()
                .currentPage(todoPage.getNumber())
                .totalItems((int) todoPage.getTotalElements())
                .totalPages(todoPage.getTotalPages())
                .todos(todos)
                .build();
    }
}
