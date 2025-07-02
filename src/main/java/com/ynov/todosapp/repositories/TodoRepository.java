package com.ynov.todosapp.repositories;


import com.ynov.todosapp.models.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TodoRepository extends PagingAndSortingRepository<Todo, Long>, CrudRepository<Todo, Long> {
    Optional<Todo> findByPublicId(Long publicId);
}
