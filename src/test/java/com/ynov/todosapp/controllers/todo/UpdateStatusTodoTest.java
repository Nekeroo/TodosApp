package com.ynov.todosapp.controllers.todo;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.dto.TodoDTO;
import com.ynov.todosapp.dto.input.TodoInputStatusDTO;
import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.exceptions.todo.InvalidStatus;
import com.ynov.todosapp.exceptions.todo.TaskNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class UpdateStatusTodoTest extends TodoControllerTest {


    @DisplayName("ÉTANT DONNÉ QUE j'ai une tâche existante, LORSQUE je change son statut vers \"TODO\", \"ONGOING\" ou \"DONE\", ALORS le statut est mis à jour avec succès\n")
    @Test
    void testUpdateStatusWithValidTodo() {
        TodoInputStatusDTO todoInputStatusDTO = TodoInputStatusDTO.builder()
                .status(StatusEnum.IN_PROGRESS.getLabel())
                .build();

        ResponseEntity<?> response = controller.updateStatus(1L, todoInputStatusDTO);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        TodoDTO todoDTO = (TodoDTO) response.getBody();

        assertNotNull(todoDTO);
        assertEquals(StatusEnum.IN_PROGRESS.getLabel(), todoDTO.getStatus());
    }

    @DisplayName("ÉTANT DONNÉ QUE je tente de changer le statut d'une tâche vers une valeur invalide, LORSQUE je soumets le changement, ALORS j'obtiens une erreur \"Invalid status. Allowed values: TODO, ONGOING, DONE\"\n")
    @Test
    void testUpdateStatusTodoWithInvalidValue() {
        TodoInputStatusDTO todoInputStatusDTO = TodoInputStatusDTO.builder()
                .status("INVALID_STATUS")
                .build();

        assertThrows(InvalidStatus.class, () -> controller.updateStatus(1L, todoInputStatusDTO));
    }

    @DisplayName("ÉTANT DONNÉ QUE je tente de changer le statut d'une tâche inexistante, LORSQUE j'utilise un ID invalide, ALORS j'obtiens une erreur \"Task not found\"\n")
    @Test
    void testUpdateStatusTodoWithInvalidID() {
        TodoInputStatusDTO todoInputStatusDTO = TodoInputStatusDTO.builder()
                .status(StatusEnum.DONE.getLabel())
                .build();

        when(service.updateTodoStatus(eq(1L), any(StatusEnum.class))).thenReturn(null);
        assertThrows(TaskNotFound.class, () -> controller.updateStatus(1L, todoInputStatusDTO));
    }

}
