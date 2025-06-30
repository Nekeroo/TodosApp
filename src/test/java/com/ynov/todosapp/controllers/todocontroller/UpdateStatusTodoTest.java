package com.ynov.todosapp.controllers.todocontroller;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.controllers.TodosController;
import com.ynov.todosapp.dto.TodoDTO;
import com.ynov.todosapp.dto.input.TodoInputStatusDTO;
import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.models.Todo;
import com.ynov.todosapp.repositories.TodoRepository;
import com.ynov.todosapp.services.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
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

        ResponseEntity<?> response = controller.updateStatus(1L, todoInputStatusDTO);

        assertTrue(response.getStatusCode().is4xxClientError());

        String erreur = (String) response.getBody();

        assertNotNull(erreur);
        assertEquals("Invalid status. Allowed values: TODO, ONGOING, DONE", erreur);
    }

    @DisplayName("ÉTANT DONNÉ QUE je tente de changer le statut d'une tâche inexistante, LORSQUE j'utilise un ID invalide, ALORS j'obtiens une erreur \"Task not found\"\n")
    @Test
    void testUpdateStatusTodoWithInvalidID() {
        TodoInputStatusDTO todoInputStatusDTO = TodoInputStatusDTO.builder()
                .status(StatusEnum.DONE.getLabel())
                .build();

        when(service.getTodoById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.updateStatus(1L, todoInputStatusDTO);

        assertTrue(response.getStatusCode().is4xxClientError());

        String erreur = (String) response.getBody();

        assertNotNull(erreur);
        assertEquals("Task not found", erreur);
    }

}
