package com.ynov.todosapp.services;

import com.ynov.todosapp.dto.input.TodoInputDTO;
import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.models.Todo;
import com.ynov.todosapp.repositories.TodoRepository;
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

    public Iterable<Todo> getAllTodos() {

        Iterable<Todo> todosIterable = todoRepository.findAll();

        List<Todo> todos = new ArrayList<>();

        for (Todo todo : todosIterable) {
            todos.add(todo);
        }
        return todos;
    }

    public void createTodo(TodoInputDTO input) {
        Todo todo = Todo.builder()
                .title(input.getTitle())
                .description(input.getDescription())
                .createdDate(LocalDate.now())
                .status(StatusEnum.TODO)
                .build();

        todoRepository.save(todo);
    }
}
