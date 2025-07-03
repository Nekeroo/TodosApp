package com.ynov.todosapp.controllers.todo;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.dto.TodosPaginedDTO;
import com.ynov.todosapp.enums.StatusEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class FilterByUserTest extends TodoControllerTest {

    @DisplayName("ÉTANT DONNÉ QUE j'ai des tâches assignées à différents utilisateurs, LORSQUE je filtre par un utilisateur spécifique, ALORS seules les tâches assignées à cet utilisateur sont retournées")
    @Test
    void testFilterByUser() {
        ResponseEntity<TodosPaginedDTO> response = controller.retrieveTodos(0, 10, "", "", "createdDate", "desc", 99L, null, null);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getTodos().isEmpty());
        
        response.getBody().getTodos().forEach(todo -> {
            assertEquals(99L,todo.getUserAffected());
        });
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai des tâches non assignées, LORSQUE je filtre par \"tâches non assignées\", ALORS seules les tâches sans assignation sont retournées")
    @Test
    void testFilterByNoUser() {
        ResponseEntity<TodosPaginedDTO> response = controller.retrieveTodos(0, 10, "", "", "createdDate", "desc", null, false, null);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getTodos().isEmpty());
        
        response.getBody().getTodos().forEach(todo -> {
            assertNull(todo.getUserAffected());
        });
    }

    @DisplayName("ÉTANT DONNÉ QUE je filtre par un utilisateur qui n'a aucune tâche assignée, LORSQUE j'applique le filtre, ALORS j'obtiens une liste vide")
    @Test
    void testFilterByUserEmptyList() {
        ResponseEntity<TodosPaginedDTO> response = controller.retrieveTodos(0, 10, "", "", "createdDate", "desc", 101L, null, null);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getTodos().isEmpty());
    }

    @DisplayName("ÉTANT DONNÉ QUE je filtre par un utilisateur inexistant, LORSQUE j'applique le filtre, ALORS j'obtiens une liste vide")
    @Test
    void testFilterByUserInvalidUser() {
        ResponseEntity<TodosPaginedDTO> response = controller.retrieveTodos(0, 10, "", "", "createdDate", "desc", 200L, null, null);
        
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getTodos().isEmpty());
    }

    @DisplayName("ÉTANT DONNÉ QUE je combine le filtre utilisateur avec d'autres filtres (statut, recherche), LORSQUE j'applique les filtres, ALORS tous les critères sont respectés")
    @Test
    void testFilterByUserWithOtherFilters() {
        ResponseEntity<TodosPaginedDTO> response = controller.retrieveTodos(0, 10, "", "todo", "createdDate", "desc", 99L, null, null);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getTodos().isEmpty());

        response.getBody().getTodos().forEach(todo -> {
            assertEquals(StatusEnum.TODO.getLabel(), todo.getStatus());
        });

        response = controller.retrieveTodos(0, 10, "tata", "", "createdDate", "desc", 100L, null, null);
        
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getTodos().isEmpty());

        response.getBody().getTodos().forEach(todo -> {
            assertTrue(todo.getTitle().contains("tata") || todo.getDescription().contains("tata"));
        });
    }
}
