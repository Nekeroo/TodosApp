package com.ynov.todosapp.controllers.todo;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.dto.TodosPaginedDTO;
import com.ynov.todosapp.exceptions.todo.InvalidPageSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class RetrieveTodosTest extends TodoControllerTest {
    @DisplayName("ÉTANT DONNÉ QUE j'ai plusieurs tâches, LORSQUE je demande la première page avec une taille de 10, ALORS j'obtiens au maximum 10 tâches et les informations de pagination (page courante, total de pages, total d'éléments)")
    @Test
    void testBodyContent() {
        final ResponseEntity<?> response = controller.retrieveTodos(1, 10, "", "", "createdDate", "");
        final TodosPaginedDTO todos = (TodosPaginedDTO) response.getBody();

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(todos);
        assertNotNull(todos.getTodos());
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai plus de 10 tâches, LORSQUE je demande la deuxième page, ALORS j'obtiens les tâches suivantes avec les bonnes informations de pagination")
    @Test
    void testPagination() {
        final ResponseEntity<?> response = controller.retrieveTodos(2, 10, "", "", "createdDate", "");
        final TodosPaginedDTO todos = (TodosPaginedDTO) response.getBody();

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(todos);
        assertNotNull(todos.getTodos());
    }

    @DisplayName("ÉTANT DONNÉ QUE je demande une page au-delà du nombre total de pages, LORSQUE j'exécute la requête, ALORS j'obtiens une liste vide avec les informations de pagination correctes")
    @Test
    void testEmptyPage() {
        final ResponseEntity<?> response = controller.retrieveTodos(3, 10, "", "", "createdDate", "");
        final TodosPaginedDTO todos = (TodosPaginedDTO) response.getBody();

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(todos);
        assertNotNull(todos.getTodos());
        assertTrue(todos.getTodos().isEmpty());
    }

    @DisplayName("ÉTANT DONNÉ QUE je spécifie une taille de page invalide (négative ou zéro), LORSQUE je fais la demande, ALORS j'obtiens une erreur \"Invalid page size\"")
    @Test
    void testInvalidPageSize() {
        assertThrows(InvalidPageSize.class, () -> controller.retrieveTodos(1, 0, "", "", "createdDate", ""));
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai aucune tâche, LORSQUE je demande la liste, ALORS j'obtiens une liste vide avec les informations de pagination (0 éléments, 0 pages)")
    @Test
    void testEmptyList() {
        todoRepository.deleteAll();
        final ResponseEntity<?> response = controller.retrieveTodos(1, 10, "", "", "createdDate", "");
        final TodosPaginedDTO todos = (TodosPaginedDTO) response.getBody();

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(todos);
        assertNotNull(todos.getTodos());
        assertEquals(0, todos.getTotalItems());
    }
}
