package com.ynov.todosapp.services;

import com.ynov.todosapp.dto.input.TodoInputDTO;
import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.models.Todo;
import com.ynov.todosapp.repositories.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    public Page<Todo> getAllTodos(int page, int size) {
        return todoRepository.findAll(PageRequest.of(page, size));
    }

    public Todo createTodo(TodoInputDTO input) {

        input.setTitle(input.getTitle().replaceAll("^\\s+|\\s+$", ""));

        String description = (input.getDescription() == null || input.getDescription().isEmpty()) ? "" : input.getDescription();

        Todo todo = Todo.builder()
                .title(input.getTitle())
                .description(description)
                .createdDate(LocalDate.now())
                .status(StatusEnum.TODO)
                .build();

        todoRepository.save(todo);
        return todo;
    }

    public void deleteTodoById(Long id) {
        todoRepository.deleteById(id);
    }

    public Todo updateTodo(Long id, TodoInputDTO input) {
        final Optional<Todo> todo = todoRepository.findById(id);

        input.setTitle(input.getTitle().replaceAll("^\\s+|\\s+$", ""));

        if (todo.isEmpty()) {
            return null;
        } else {
            todo.get().setTitle(input.getTitle().trim());
            todo.get().setDescription(input.getDescription().trim());
            todoRepository.save(todo.get());
            return todo.get();
        }
    }

    public Todo updateTodoStatus(Long id, StatusEnum status) {
        final Optional<Todo> todo = todoRepository.findById(id);

        if (todo.isEmpty()) {
            return null;
        } else {
            todo.get().setStatus(status);
            todoRepository.save(todo.get());
            return todo.get();
        }
    }
}
