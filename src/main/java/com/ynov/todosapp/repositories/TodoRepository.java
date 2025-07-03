package com.ynov.todosapp.repositories;


import com.ynov.todosapp.enums.PriorityEnum;
import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.models.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TodoRepository extends PagingAndSortingRepository<Todo, Long>, CrudRepository<Todo, Long> {
    @Query("SELECT t FROM Todo t WHERE " +
            "(:status IS NULL OR t.status = :status) AND " +
            "(:priority IS NULL OR t.priority = :priority) AND " +
            "((:userId IS NULL AND (" +
            "(:isAssigned IS NULL) OR " +
            "(:isAssigned = false AND t.userAffected IS NULL) OR " +
            "(:isAssigned = true AND t.userAffected IS NOT NULL)" +
            ")) OR " +
            "(:userId IS NOT NULL AND t.userAffected.id = :userId)) AND " +
            "(:query IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :query, '%')))"
    )
    Page<Todo> searchTodos(@Param("status") StatusEnum status,
                           @Param("userId") Long userId,
                           @Param("isAssigned") Boolean isAssigned,
                           @Param("priority") PriorityEnum priority,
                           @Param("query") String query,
                           Pageable pageable);

    @Query("DELETE FROM Todo t WHERE t.userAffected.id = :id")
    @Modifying
    void deleteByUserAffected(Long id);


    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("""
        UPDATE Todo t
           SET t.userAffected = NULL
         WHERE t.userAffected.id = :userId
    """)
    void clearUserAffectedByUserId(@Param("userId") Long userId);
}
