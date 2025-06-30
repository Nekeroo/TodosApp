package com.ynov.todosapp.controllers;

import com.ynov.todosapp.dto.TodoDTO;
import com.ynov.todosapp.dto.input.TodoInputDTO;
import com.ynov.todosapp.models.Todo;
import com.ynov.todosapp.services.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/todos/")
public class TodosController {

    private final TodoService todoService;

    public TodosController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("")
    public ResponseEntity<?> retrieveTodos() {
        return ResponseEntity.ok().body(todoService.getAllTodos());
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
                            .title(todo.get().getTitle())
                            .description(todo.get().getDescription())
                            .status(todo.get().getStatus().name())
                            .build()
            );
        } else {
            return ResponseEntity.status(404).body("Todo not found");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createTodo(@RequestBody TodoInputDTO input) {
        if (input.getTitle() == null || input.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body("Title cannot be empty");
        }

        if (input.getTitle().length() > 100) {
            return ResponseEntity.badRequest().body("Title is too long");
        }

        todoService.createTodo(input);
        return ResponseEntity.ok().body("Todo created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable Long id, @RequestBody TodoInputDTO input) {
        return null;
    }

    @PutMapping("/statut/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id) {
        return null;
    }

}
