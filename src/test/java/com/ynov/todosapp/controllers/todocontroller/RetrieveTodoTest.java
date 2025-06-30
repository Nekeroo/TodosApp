package com.ynov.todosapp.controllers.todocontroller;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.controllers.TodosController;
import com.ynov.todosapp.dto.TodoDTO;
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
public class RetrieveTodoTest extends TodoControllerTest {

    @DisplayName("ÉTANT DONNÉ QUE j'ai une tâche existante avec ID valide, LORSQUE je consulte cette tâche, ALORS j'obtiens tous ses détails : ID, titre, description, statut, date de création, etc..")
    @Test
    void testGetOneTodo() {

        ResponseEntity<?> repsonse = controller.retrieveTodoById("1");

        assertTrue(repsonse.getStatusCode().is2xxSuccessful());

        TodoDTO todoDTO = (TodoDTO) repsonse.getBody();
        assertNotNull(todoDTO);
        assertNotNull(todoDTO.getId());
        assertNotNull(todoDTO.getTitle());
        assertNotNull(todoDTO.getDescription());
        assertNotNull(todoDTO.getCreatedDate());
        assertNotNull(todoDTO.getStatus());

    }

    @DisplayName("ÉTANT DONNÉ QUE je consulte une tâche avec un ID inexistant, LORSQUE je fais la demande, ALORS j'obtiens une erreur \"Task not found\" avec, si web, le code 404\n")
    @Test
    void testGetOneTodoNotFound() {

        when(service.getTodoById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> repsonse = controller.retrieveTodoById("1");

        assertTrue(repsonse.getStatusCode().is4xxClientError());
        String body = (String) repsonse.getBody();

        assertEquals("Task not found", body);
    }

    @DisplayName("ÉTANT DONNÉ QUE je consulte une tâche avec un ID au mauvais format, LORSQUE je fais la demande, ALORS j'obtiens une erreur \"Invalid ID format\"\n")
    @Test
    void testGetOneTodoWithIdWithIncorrectFormat() {
        ResponseEntity<?> repsonse = controller.retrieveTodoById("azerty");

        assertTrue(repsonse.getStatusCode().is4xxClientError());

        String body = (String) repsonse.getBody();

        assertEquals("Invalid ID format", body);
    }

}
