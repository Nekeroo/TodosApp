package com.ynov.todosapp.controllers;

import com.ynov.todosapp.dto.TodoAssignInputDTO;
import com.ynov.todosapp.dto.TodosPaginedDTO;
import com.ynov.todosapp.dto.input.TodoInputDTO;
import com.ynov.todosapp.dto.input.TodoInputStatusDTO;
import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.exceptions.todo.InvalidIDFormat;
import com.ynov.todosapp.exceptions.todo.InvalidStatus;
import com.ynov.todosapp.exceptions.todo.TaskNotFound;
import com.ynov.todosapp.mapper.TodoMapper;
import com.ynov.todosapp.models.Todo;
import com.ynov.todosapp.models.User;
import com.ynov.todosapp.services.TodoService;
import com.ynov.todosapp.services.UserService;
import com.ynov.todosapp.utils.TodoValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos/")
public class TodosController {

    private final TodoService todoService;
    private final UserService userService;

    public TodosController(TodoService todoService, UserService userService) {
        this.todoService = todoService;
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<?> retrieveTodos(@RequestParam(defaultValue = "0") int page) {
        TodosPaginedDTO todos = TodoMapper.todoPageToDTO(todoService.getAllTodos(page));
        return ResponseEntity.ok().body(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveTodoById(@PathVariable String id) {
        try {
            final Todo todo = todoService.getTodoById(Long.parseLong(id));
            return ResponseEntity.ok().body(TodoMapper.todoToDTO(todo));
        } catch (NumberFormatException e) {
            throw new InvalidIDFormat();
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createTodo(@RequestBody TodoInputDTO input) {
        TodoValidator.validateTitle(input.getTitle());
        TodoValidator.validateDescription(input.getDescription());

        final Todo todo = todoService.createTodo(input);

        return ResponseEntity.ok().body(TodoMapper.todoToDTO(todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodoById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable Long id, @RequestBody TodoInputDTO input) {
        if (input.getDescription() == null) {
            input.setDescription("");
        }

        TodoValidator.validateTitle(input.getTitle());
        TodoValidator.validateDescription(input.getDescription());

        final Todo todo = todoService.updateTodo(id, input);
        return ResponseEntity.ok().body(TodoMapper.todoToDTO(todo));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody TodoInputStatusDTO input) {
        final StatusEnum status = StatusEnum.getStatusByString(input.getStatus());

        if (status == null) {
            throw new InvalidStatus();
        }

        final Todo todo = todoService.updateTodoStatus(id, status);

        if (todo == null) {
            throw new TaskNotFound();
        } else {
            return ResponseEntity.ok().body(TodoMapper.todoToDTO(todo));
        }
    }

    @PutMapping("/assign/{publicId}")
    public ResponseEntity<?> assignUserToTodo(@PathVariable Long publicId, @RequestBody TodoAssignInputDTO input) {
        final Todo todo = todoService.getTodoByPublicId(publicId);

        final User user = userService.getUserByEmail(input.getEmail());

        todo.setUserAffected(user);
        todoService.saveTodo(todo);

        return ResponseEntity.ok().body(TodoMapper.todoToDTO(todo));
    }

}
