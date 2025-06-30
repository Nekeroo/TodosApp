package com.ynov.todosapp.repositories;


import com.ynov.todosapp.models.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Long> {
}
