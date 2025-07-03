package com.ynov.todosapp.services;

import com.ynov.todosapp.dto.input.TodoInputDTO;
import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.enums.TodoSort;
import com.ynov.todosapp.exceptions.todo.InvalidFilterStatus;
import com.ynov.todosapp.exceptions.todo.InvalidSortCriteria;
import com.ynov.todosapp.exceptions.todo.TaskNotFound;
import com.ynov.todosapp.mapper.TodoMapper;
import com.ynov.todosapp.models.Todo;
import com.ynov.todosapp.repositories.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void saveTodo(Todo todo) {
        todoRepository.save(todo);
    }

    public Page<Todo> getAllTodos(int page, int size, String query, String statusString, Long userId, Boolean isAssigned, String sortBy, String sortDirection) {
        TodoSort todoSort = TodoSort.getSortByString(sortBy)
                .orElseThrow(InvalidSortCriteria::new);
        StatusEnum status = StatusEnum.getStatusByString(statusString);
        Sort.Direction direction = sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, todoSort.getLabel()));

        if (!statusString.isEmpty() && status == null) {
            throw new InvalidFilterStatus();
        }

        return todoRepository.searchTodos(status, userId, isAssigned, query.trim(), pageRequest);
    }

    public Todo createTodo(TodoInputDTO input) {
        input.setTitle(input.getTitle().replaceAll("^\\s+|\\s+$", ""));

        String description = (input.getDescription() == null || input.getDescription().isEmpty()) ? "" : input.getDescription();
        input.setDescription(description);
        Todo todo = TodoMapper.todoInputDTOToTodo(input, LocalDate.now(), StatusEnum.TODO);

        todoRepository.save(todo);
        return todo;
    }

    public void deleteTodoById(Long id) {
        todoRepository.deleteById(id);
    }

    public Todo updateTodo(Long id, TodoInputDTO input) {
        Todo todo = getTodoById(id);

        input.setTitle(input.getTitle().replaceAll("^\\s+|\\s+$", ""));

        todo.setTitle(input.getTitle().trim());
        todo.setDescription(input.getDescription().trim());
        todo = todoRepository.save(todo);
        return todo;
    }

    public Todo updateTodoStatus(Long id, StatusEnum status) {
        final Todo todo = getTodoById(id);

        todo.setStatus(status);
        todoRepository.save(todo);
        return todo;
    }

    @Transactional
    public void deleteTodoByUserIdAffected(Long id) {
        todoRepository.deleteByUserAffected(id);
    }

    @Transactional
      public void deleteUserAffectedOnTodo(Long id) {
        todoRepository.clearUserAffectedByUserId(id);
    }
}
