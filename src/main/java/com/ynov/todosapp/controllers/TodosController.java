package com.ynov.todosapp.controllers;

import com.ynov.todosapp.dto.input.TodoInputDTO;
import com.ynov.todosapp.services.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
public class TodosController {

    private final TodoService todoService;

    public TodosController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/")
    public ResponseEntity<?> retrieveTodos() {
        return ResponseEntity.ok().body(todoService.getAllTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveTodoById(@PathVariable int id) {
        return null;
    }

    @PostMapping("/")
    public ResponseEntity<?> createTodo(@RequestBody TodoInputDTO input) {
        return null;
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
