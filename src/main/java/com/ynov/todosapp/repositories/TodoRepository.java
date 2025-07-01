package com.ynov.todosapp.repositories;


import com.ynov.todosapp.models.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TodoRepository extends PagingAndSortingRepository<Todo, Long>, CrudRepository<Todo, Long> {
    Page<Todo> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description, Pageable pageable);

}
