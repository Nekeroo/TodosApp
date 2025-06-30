package com.ynov.todosapp.services;

import com.ynov.todosapp.models.Todo;
import com.ynov.todosapp.repositories.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Iterable<Todo> getAllTodos() {

        Iterable<Todo> todosIterable = todoRepository.findAll();

        List<Todo> todos = new ArrayList<>();

        for (Todo todo : todosIterable) {
            todos.add(todo);
        }
        return todos;
    }
}
