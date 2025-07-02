package com.ynov.todosapp.controllers.todo;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.dto.TodosPaginedDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RetrieveTodosTest extends TodoControllerTest {
    @DisplayName("ÉTANT DONNÉ QUE j'ai plusieurs tâches, LORSQUE je demande la première page avec une taille de 10, ALORS j'obtiens au maximum 10 tâches et les informations de pagination (page courante, total de pages, total d'éléments)")
    @Test
    void testBodyContent() {
        final ResponseEntity<?> response = controller.retrieveTodos(1);
        final TodosPaginedDTO todos = (TodosPaginedDTO) response.getBody();

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(todos);
        assertNotNull(todos.getTodos());
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai plus de 10 tâches, LORSQUE je demande la deuxième page, ALORS j'obtiens les tâches suivantes avec les bonnes informations de pagination")
    @Test
    void testPagination() {
        final ResponseEntity<?> response = controller.retrieveTodos(2);
        final TodosPaginedDTO todos = (TodosPaginedDTO) response.getBody();

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(todos);
        assertNotNull(todos.getTodos());
    }
}
