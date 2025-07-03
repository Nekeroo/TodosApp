package com.ynov.todosapp.mapper;

import com.ynov.todosapp.dto.TodoDTO;
import com.ynov.todosapp.dto.TodosPaginedDTO;
import com.ynov.todosapp.dto.input.TodoInputDTO;
import com.ynov.todosapp.enums.PriorityEnum;
import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.models.Todo;
import com.ynov.todosapp.models.User;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoMapper {
    public static TodoDTO todoToDTO(Todo todo) {
        User user = todo.getUserAffected();
        return TodoDTO.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .createdDate(todo.getCreatedDate())
                .status(todo.getStatus().getLabel())
                .priority(todo.getPriority().getLabel())
                .deadline(todo.getDeadline())
                .userAffected(user == null ? null : user.getId())
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

    public static Todo todoInputDTOToTodo(TodoInputDTO input, LocalDate createdDate, StatusEnum status) {
        return Todo.builder()
                .title(input.getTitle())
                .description(input.getDescription())
                .createdDate(createdDate)
                .priority(PriorityEnum.fromString(input.getPriority()).orElse(PriorityEnum.NORMAL))
                .status(status)
                .deadline(input.getDeadline())
                .build();
    }
}
