package com.ynov.todosapp.controllers;

import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.models.Todo;
import com.ynov.todosapp.repositories.TodoRepository;
import com.ynov.todosapp.services.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public abstract class TodoControllerTest {

    @Mock
    protected TodoRepository repository;

    @InjectMocks
    protected TodoService service;

    protected LocalDate creationDate;

    protected TodosController controller;
    protected List<Todo> savedTodos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new TodosController(service);
        savedTodos = new ArrayList<>();

        creationDate = LocalDate.now();

        Todo existingTodo = Todo.builder()
                .id(1L)
                .title("Todo Title")
                .description("Todo Description")
                .status(StatusEnum.TODO)
                .createdDate(creationDate)
                .build();

        savedTodos.add(existingTodo);

        when(service.getTodoById(1L)).thenReturn(Optional.of(existingTodo));

        when(repository.findAll()).thenReturn(savedTodos);

        when(repository.findById(1L)).thenReturn(Optional.of(existingTodo));

        // save just returns the entity passed in
        when(repository.save(any(Todo.class))).thenAnswer(invocation -> {
            Todo entity = invocation.getArgument(0, Todo.class);
            return entity;
        });

    }
}