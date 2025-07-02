package com.ynov.todosapp.controllers.todocontroller;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.dto.TodoDTO;
import com.ynov.todosapp.dto.TodosPaginedDTO;
import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.models.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class SearchQueryTodosTest extends TodoControllerTest {
    
    private Todo todoWithTitleMatch;
    private Todo todoWithDescriptionMatch;
    private Todo todoWithBothMatch;
    
    @BeforeEach
    void setUp() {
        todoWithTitleMatch = Todo.builder()
                .id(1L)
                .title("Todo Title toto")
                .description("Todo Description")
                .status(StatusEnum.TODO)
                .createdDate(creationDate)
                .build();
                
        todoWithDescriptionMatch = Todo.builder()
                .id(2L)
                .title("Todo Title")
                .description("Todo Description toto")
                .status(StatusEnum.TODO)
                .createdDate(creationDate)
                .build();
                
        todoWithBothMatch = Todo.builder()
                .id(3L)
                .title("Todo Title toto")
                .description("Todo Description toto")
                .status(StatusEnum.TODO)
                .createdDate(creationDate)
                .build();
    }
    
    private void configureMockForSearch(String query, List<Todo> todos) {
        Page<Todo> todoPage = new PageImpl<>(todos);
        when(service.getAllTodos(anyInt(), eq(10), eq(query), eq(""), eq(""), eq("")))
                .thenReturn(todoPage);
    }
    
    private void configureMockForEmptySearch(String query) {
        when(service.getAllTodos(anyInt(), eq(10), eq(query), eq(""), eq(""), eq("")))
                .thenReturn(Page.empty());
    }
    
    private ResponseEntity<TodosPaginedDTO> executeSearch(String query) {
        return controller.retrieveTodos(0, 10, query, "", "", "");
    }

    @DisplayName("- ÉTANT DONNÉ QUE j'ai des tâches contenant un mot-clé dans le titre, LORSQUE je recherche ce terme, ALORS seules les tâches correspondantes sont retournées")
    @Test
    void testSearchByTitle() {
        configureMockForSearch("toto", List.of(todoWithTitleMatch));

        ResponseEntity<TodosPaginedDTO> response = executeSearch("toto");

        assertNotNull(response);
        assertNotNull(response.getBody());

        response.getBody().getTodos().forEach(todo -> {
            assertTrue(todo.getTitle().contains("toto"));
        });
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai des tâches contenant un mot-clé dans la description, LORSQUE je recherche ce terme, ALORS seules les tâches correspondantes sont retournées")
    @Test
    void testSearchByDescription() {
        configureMockForSearch("toto", List.of(todoWithDescriptionMatch));

        ResponseEntity<TodosPaginedDTO> response = executeSearch("toto");

        assertNotNull(response);
        assertNotNull(response.getBody());

        response.getBody().getTodos().forEach(todo -> {
            assertTrue(todo.getDescription().contains("toto"));
        });
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai des tâches contenant un mot-clé dans le titre ET la description, LORSQUE je recherche ce terme, ALORS toutes ces tâches sont retournées (sans doublon)")
    @Test
    void testSearchByTitleAndDescription() {
        configureMockForSearch("toto", List.of(todoWithBothMatch));

        ResponseEntity<TodosPaginedDTO> response = executeSearch("toto");

        assertNotNull(response);
        assertNotNull(response.getBody());

        HashSet<TodoDTO> todos = new HashSet<>(response.getBody().getTodos());

        assertEquals(response.getBody().getTodos().size(), todos.size());

        todos.forEach(todo -> {
            assertTrue(todo.getTitle().contains("toto") || todo.getDescription().contains("toto"));
        });
    }

    @DisplayName("ÉTANT DONNÉ QUE je recherche un terme inexistant, LORSQUE j'exécute la recherche, ALORS j'obtiens une liste vide")
    @Test
    void testSearchByTermNotFound() {
        configureMockForEmptySearch("toto");
        
        ResponseEntity<TodosPaginedDTO> response = executeSearch("toto");
        
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getTodos().isEmpty());
    }

    @DisplayName("ÉTANT DONNÉ QUE je recherche avec des majuscules/minuscules, LORSQUE j'exécute la recherche, ALORS la recherche est insensible à la casse")
    @Test
    void testSearchByTermCaseInsensitive() {
        configureMockForSearch("TOTO", List.of(todoWithDescriptionMatch));

        ResponseEntity<TodosPaginedDTO> response = executeSearch("TOTO");

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getTodos().isEmpty());
    }
}
