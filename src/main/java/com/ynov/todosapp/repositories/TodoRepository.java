package com.ynov.todosapp.repositories;


import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.models.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TodoRepository extends PagingAndSortingRepository<Todo, Long>, CrudRepository<Todo, Long> {
//    Page<Todo> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
//            String title, String description, Pageable pageable);
//
//    Page<Todo> findByStatusAndTitleContainingIgnoreCaseOrStatusAndDescriptionContainingIgnoreCase(
//            StatusEnum status1, String title,
//            StatusEnum status2, String description,
//            Pageable pageable
//    );

    Optional<Todo> findByPublicId(Long publicId);

    @Query("SELECT t FROM Todo t WHERE " +
            "(:status IS NULL OR t.status = :status) AND " +
            "(:query IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Todo> searchTodos(@Param("status") StatusEnum status,
                           @Param("query") String query,
                           Pageable pageable);
}
