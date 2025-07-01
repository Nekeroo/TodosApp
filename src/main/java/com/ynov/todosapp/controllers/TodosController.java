package com.ynov.todosapp.controllers;

import com.ynov.todosapp.dto.TodoDTO;
import com.ynov.todosapp.dto.TodosPaginedDTO;
import com.ynov.todosapp.dto.input.TodoInputDTO;
import com.ynov.todosapp.dto.input.TodoInputStatusDTO;
import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.models.Todo;
import com.ynov.todosapp.services.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos/")
public class TodosController {

    private final TodoService todoService;

    public TodosController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("")
    public ResponseEntity<?> retrieveTodos(@RequestParam(defaultValue = "0") int page) {
        Page<Todo> todoPage = todoService.getAllTodos(page);

        List<TodoDTO> todos = new ArrayList<>(todoPage.getContent().stream()
                .map(todo -> TodoDTO.builder()
                        .id(todo.getId())
                        .title(todo.getTitle())
                        .description(todo.getDescription())
                        .createdDate(todo.getCreatedDate())
                        .status(todo.getStatus().getLabel())
                        .build())
                .toList());

        TodosPaginedDTO todosPagined = TodosPaginedDTO.builder()
                .currentPage(page)
                .totalItems((int) todoPage.getTotalElements())
                .totalPages(todoPage.getTotalPages())
                .todos(todos)
                .build();

        return ResponseEntity.ok().body(todosPagined);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveTodoById(@PathVariable String id) {
        final Optional<Todo> todo;

        try {
            todo = todoService.getTodoById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid ID format");
        }

        if (todo.isPresent()) {
            return ResponseEntity.ok().body(
                    TodoDTO.builder()
                            .id(todo.get().getId())
                            .title(todo.get().getTitle())
                            .description(todo.get().getDescription())
                            .createdDate(todo.get().getCreatedDate())
                            .status(todo.get().getStatus().getLabel())
                            .build()
            );
        } else {
            return ResponseEntity.status(404).body("Task not found");
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createTodo(@RequestBody TodoInputDTO input) {
        if (input.getTitle() == null || input.getTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Title is required");
        }

        if (input.getTitle().length() > 100) {
            return ResponseEntity.badRequest().body("Title cannot exceed 100 characters");
        }

        if (input.getDescription() != null && input.getDescription().length() > 500) {
            return ResponseEntity.badRequest().body("Description cannot exceed 500 characters");
        }

        final Todo todo = todoService.createTodo(input);
        return ResponseEntity.ok().body(
                TodoDTO.builder()
                        .id(todo.getId())
                        .title(todo.getTitle())
                        .description(todo.getDescription())
                        .createdDate(todo.getCreatedDate())
                        .status(todo.getStatus().getLabel())
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodoById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable Long id, @RequestBody TodoInputDTO input) {
        if (input.getTitle() == null || input.getTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Title is required");
        }

        if (input.getTitle().trim().length() > 100) {
            return ResponseEntity.badRequest().body("Title cannot exceed 100 characters");
        }

        if (input.getDescription() == null) {
            input.setDescription("");
        }

        if (input.getDescription().trim().length() > 500) {
            return ResponseEntity.badRequest().body("Description cannot exceed 500 characters");
        }

        final Todo todo = todoService.updateTodo(id, input);

        if (todo != null) {
            return ResponseEntity.ok().body(
                    TodoDTO.builder()
                            .id(todo.getId())
                            .title(todo.getTitle())
                            .createdDate(todo.getCreatedDate())
                            .description(todo.getDescription())
                            .status(todo.getStatus().getLabel())
                            .build()
            );
        }

        return ResponseEntity.status(404).body("Task not found");
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody TodoInputStatusDTO input) {
        final StatusEnum status = StatusEnum.getStatusByString(input.getStatus());

        if (status == null) {
            return ResponseEntity.badRequest().body("Invalid status. Allowed values: TODO, ONGOING, DONE");
        }

        final Todo todo = todoService.updateTodoStatus(id, status);

        if (todo == null) {
            return ResponseEntity.status(404).body("Task not found");
        } else {
            return ResponseEntity.ok().body(
                    TodoDTO.builder()
                            .id(todo.getId())
                            .title(todo.getTitle())
                            .description(todo.getDescription())
                            .createdDate(todo.getCreatedDate())
                            .status(todo.getStatus().getLabel())
                            .build()
            );
        }
    }

}
