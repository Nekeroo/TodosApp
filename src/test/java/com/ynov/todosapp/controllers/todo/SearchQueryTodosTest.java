package com.ynov.todosapp.controllers.todo;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.dto.TodoDTO;
import com.ynov.todosapp.dto.TodosPaginedDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class SearchQueryTodosTest extends TodoControllerTest {

    @DisplayName("- ÉTANT DONNÉ QUE j'ai des tâches contenant un mot-clé dans le titre, LORSQUE je recherche ce terme, ALORS seules les tâches correspondantes sont retournées")
    @Test
    void testSearchByTitle() {

        ResponseEntity<TodosPaginedDTO> response = controller.retrieveTodos(0, 10,"toto" , "", "createdDate", "", null, null);

        assertNotNull(response);
        assertNotNull(response.getBody());

        response.getBody().getTodos().forEach(todo -> {
            assertTrue(todo.getTitle().contains("toto"));
        });
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai des tâches contenant un mot-clé dans la description, LORSQUE je recherche ce terme, ALORS seules les tâches correspondantes sont retournées")
    @Test
    void testSearchByDescription() {

        ResponseEntity<TodosPaginedDTO> response = controller.retrieveTodos(0, 10,"toto" , "", "createdDate", "", null, null);

        assertNotNull(response);
        assertNotNull(response.getBody());

        response.getBody().getTodos().forEach(todo -> {
            assertTrue(todo.getDescription().contains("toto"));
        });
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai des tâches contenant un mot-clé dans le titre ET la description, LORSQUE je recherche ce terme, ALORS toutes ces tâches sont retournées (sans doublon)")
    @Test
    void testSearchByTitleAndDescription() {

        ResponseEntity<TodosPaginedDTO> response = controller.retrieveTodos(0, 10,"toto" , "", "createdDate", "", null, null);

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

        ResponseEntity<TodosPaginedDTO> response = controller.retrieveTodos(0, 10,"zzzzzzz" , "", "createdDate", "", null, null);
        
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getTodos().isEmpty());
    }

    @DisplayName("ÉTANT DONNÉ QUE je recherche avec des majuscules/minuscules, LORSQUE j'exécute la recherche, ALORS la recherche est insensible à la casse")
    @Test
    void testSearchByTermCaseInsensitive() {

        ResponseEntity<TodosPaginedDTO> response = controller.retrieveTodos(0, 10,"TOTO" , "", "createdDate", "", null, null);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getTodos().isEmpty());
    }
}
