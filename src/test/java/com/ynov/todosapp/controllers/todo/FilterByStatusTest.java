package com.ynov.todosapp.controllers.todo;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.dto.TodosPaginedDTO;
import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.exceptions.todo.InvalidFilterStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class FilterByStatusTest extends TodoControllerTest {

    
    @DisplayName("ÉTANT DONNÉ QUE j'ai des tâches avec différents statuts, LORSQUE je filtre par \"TODO\", \"ONGOING\" ou \"DONE\", ALORS seules les tâches avec le statut correspondant sont retournées")
    @Test
    void testFilterByStatus() {
        ResponseEntity<TodosPaginedDTO> response = controller.retrieveTodos(0, 10, "", "progress", "createdDate", "", null, null, null);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getTodos().isEmpty());

        response.getBody().getTodos().forEach(todo -> {
            assertEquals(StatusEnum.IN_PROGRESS.getLabel(), todo.getStatus());
        });
    }

    @DisplayName("ÉTANT DONNÉ QUE je filtre par un statut et qu'aucune tâche ne correspond, LORSQUE j'applique le filtre, ALORS j'obtiens une liste vide")
    @Test
    void testFilterByStatusEmptyList() {
        todoRepository.deleteAll();
        ResponseEntity<TodosPaginedDTO> response = controller.retrieveTodos(0, 10, "", "progress", "createdDate", "", null, null, null);
        
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getTodos().isEmpty());
    }

    @DisplayName("ÉTANT DONNÉ QUE je filtre par un statut invalide, LORSQUE j'applique le filtre, ALORS j'obtiens une erreur \"Invalid filter status\"")
    @Test
    void testFilterByStatusInvalidStatus() {
        assertThrows(InvalidFilterStatus.class, () ->  controller.retrieveTodos(0, 10, "", "invalid", "createdDate", "", null, null, null));
    }
}
