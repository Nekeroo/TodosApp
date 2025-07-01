package com.ynov.todosapp.controllers;

import com.ynov.todosapp.dto.input.TodoInputDTO;
import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.models.Todo;
import com.ynov.todosapp.repositories.TodoRepository;
import com.ynov.todosapp.services.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public abstract class TodoControllerTest {

    @Mock
    protected TodoRepository repository;

    @Mock
    protected TodoService service;

    protected LocalDate creationDate;

    protected TodosController controller;
    protected List<Todo> savedTodos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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

        // Configure all mocks before creating the controller
        // 1. For getAllTodos (RetrieveTodosTest)
        List<Todo> allTodos = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            allTodos.add(existingTodo);
        }
        Page<Todo> todoPage = new PageImpl<>(allTodos);
        when(service.getAllTodos(anyInt(), eq(10), eq(""))).thenReturn(todoPage);
        
        // 2. For getTodoById (RetrieveTodoTest)
        when(service.getTodoById(1L)).thenReturn(existingTodo);
        
        // 3. For updateTodoStatus (UpdateStatusTodoTest)
        when(service.updateTodoStatus(eq(1L), any(StatusEnum.class))).thenAnswer(invocation -> {
            StatusEnum newStatus = invocation.getArgument(1, StatusEnum.class);
            Todo updatedTodo = Todo.builder()
                .id(1L)
                .title("Todo Title")
                .description("Todo Description")
                .status(newStatus)
                .createdDate(creationDate)
                .build();
            return updatedTodo;
        });
        
        // 4. For createTodo
        when(service.createTodo(any())).thenAnswer(invocation -> existingTodo);
        
        // 5. For updateTodo
        when(service.updateTodo(eq(1L), any())).thenAnswer(invocation -> {
            TodoInputDTO todoDTO = invocation.getArgument(1);
            Todo updatedTodo = Todo.builder()
                .id(1L)
                .title(todoDTO.getTitle())
                .description(todoDTO.getDescription())
                .status(StatusEnum.TODO)
                .createdDate(creationDate)
                .build();
            return updatedTodo;
        });
        
        // Repository mocks if needed
        when(repository.findById(1L)).thenReturn(Optional.of(existingTodo));
        when(repository.save(any(Todo.class))).thenAnswer(invocation -> {
            Todo entity = invocation.getArgument(0, Todo.class);
            return entity;
        });
        
        // Create controller after all mocks are set up
        controller = new TodosController(service);
    }
}
