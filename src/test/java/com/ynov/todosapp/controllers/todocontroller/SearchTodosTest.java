package com.ynov.todosapp.controllers.todocontroller;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.dto.TodosPaginedDTO;
import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.models.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class SearchTodosTest extends TodoControllerTest {
    @DisplayName("- ÉTANT DONNÉ QUE j'ai des tâches contenant un mot-clé dans le titre, LORSQUE je recherche ce terme, ALORS seules les tâches correspondantes sont retournées")
    @Test
    void testSearchByTitle() {
        Todo existingTodo = Todo.builder()
                .id(2L)
                .title("Todo Title toto")
                .description("Todo Description")
                .status(StatusEnum.TODO)
                .createdDate(creationDate)
                .build();

        repository.save(existingTodo);
        Page<Todo> todoPage = new PageImpl<>(List.of(existingTodo));
        when(service.getAllTodos(anyInt(), eq("toto"))).thenReturn(todoPage);;

        ResponseEntity<TodosPaginedDTO> response = controller.retrieveTodos(0, "toto");

        assertNotNull(response);
        assertNotNull(response.getBody());

        response.getBody().getTodos().forEach(todo -> {
            assertTrue(todo.getTitle().contains("toto"));
        });
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai des tâches contenant un mot-clé dans la description, LORSQUE je recherche ce terme, ALORS seules les tâches correspondantes sont retournées")
    @Test
    void testSearchByDescription() {
        Todo existingTodo = Todo.builder()
                .id(2L)
                .title("Todo Title")
                .description("Todo Description toto")
                .status(StatusEnum.TODO)
                .createdDate(creationDate)
                .build();

        repository.save(existingTodo);
        Page<Todo> todoPage = new PageImpl<>(List.of(existingTodo));
        when(service.getAllTodos(anyInt(), eq("toto"))).thenReturn(todoPage);;

        ResponseEntity<TodosPaginedDTO> response = controller.retrieveTodos(0, "toto");

        assertNotNull(response);
        assertNotNull(response.getBody());

        response.getBody().getTodos().forEach(todo -> {
            assertTrue(todo.getDescription().contains("toto"));
        });
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai des tâches contenant un mot-clé dans le titre ET la description, LORSQUE je recherche ce terme, ALORS toutes ces tâches sont retournées (sans doublon)")
    @Test
    void testSearchByTitleAndDescription() {
        Todo existingTodo = Todo.builder()
                .id(2L)
                .title("Todo Title toto")
                .description("Todo Description toto")
                .status(StatusEnum.TODO)
                .createdDate(creationDate)
                .build();

        repository.save(existingTodo);
        Page<Todo> todoPage = new PageImpl<>(List.of(existingTodo));
        when(service.getAllTodos(anyInt(), eq("toto"))).thenReturn(todoPage);
        ;

        ResponseEntity<TodosPaginedDTO> response = controller.retrieveTodos(0, "toto");

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getTodos().iterator(), List.of(existingTodo));

        response.getBody().getTodos().forEach(todo -> {
            assertTrue(todo.getTitle().contains("toto") || todo.getDescription().contains("toto"));
        });
    }
}
