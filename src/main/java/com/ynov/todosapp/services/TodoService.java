package com.ynov.todosapp.services;

import com.ynov.todosapp.dto.input.TodoInputDTO;
import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.exceptions.TaskNotFound;
import com.ynov.todosapp.models.Todo;
import com.ynov.todosapp.repositories.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(TaskNotFound::new);
    }

    public Page<Todo> getAllTodos(int page) {
        return todoRepository.findAll(PageRequest.of(page, 10));
    }

    public Todo createTodo(TodoInputDTO input) {

        input.setTitle(input.getTitle().replaceAll("^\\s+|\\s+$", ""));

        String description = (input.getDescription() == null || input.getDescription().isEmpty()) ? "" : input.getDescription();

        // TODO: use mapper
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
        final Todo todo = todoRepository.findById(id)
                .orElseThrow(TaskNotFound::new);

        input.setTitle(input.getTitle().replaceAll("^\\s+|\\s+$", ""));

        todo.setTitle(input.getTitle().trim());
        todo.setDescription(input.getDescription().trim());
        todoRepository.save(todo);
        return todo;
    }

    public Todo updateTodoStatus(Long id, StatusEnum status) {
        final Todo todo = todoRepository.findById(id)
                .orElseThrow(TaskNotFound::new);

        todo.setStatus(status);
        todoRepository.save(todo);
        return todo;
    }
}
