package com.ynov.todosapp.controllers.todo;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.dto.TodoDTO;
import com.ynov.todosapp.dto.input.TodoInputDTO;
import com.ynov.todosapp.exceptions.todo.TaskNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DeadlineTest extends TodoControllerTest {

    @DisplayName("ÉTANT DONNÉ QUE j'ai une tâche existante, LORSQUE je définis une date d'échéance future valide, ALORS la date est enregistrée et visible dans les détails")
    @Test
    void testSetFutureDeadline() {
        LocalDate date = LocalDate.now().plusDays(7);
        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("Toto title")
                .deadline(date)
                .build();

        ResponseEntity<?> response = controller.updateTodo(103L, inputDTO);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        TodoDTO todoModified = (TodoDTO) response.getBody();

        assertNotNull(todoModified);
        assertNotNull(todoModified.getDeadline());
        assertEquals(date, todoModified.getDeadline());
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai une tâche avec échéance, LORSQUE je modifie la date d'échéance, ALORS la nouvelle date remplace l'ancienne")
    @Test
    void testUpdateDeadline() {
        LocalDate date = LocalDate.now().plusDays(7);
        TodoInputDTO updateDTO = TodoInputDTO.builder()
                .title("Toto title")
                .deadline(date)
                .build();

        ResponseEntity<?> response = controller.updateTodo(99L, updateDTO);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        TodoDTO todoModified = (TodoDTO) response.getBody();
        assertNotNull(todoModified);
        assertNotNull(todoModified.getDeadline(), todoModified.getDeadline().toString());
        assertEquals(date, todoModified.getDeadline());
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai une tâche avec échéance, LORSQUE je supprime la date d'échéance (la définir à null), ALORS la tâche n'a plus d'échéance")
    @Test
    void testRemoveDeadline() {
        TodoInputDTO updateDTO = TodoInputDTO.builder()
                .title("Toto title")
                .deadline(null)
                .build();


        ResponseEntity<?> response = controller.updateTodo(99L, updateDTO);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        TodoDTO todoModified = (TodoDTO) response.getBody();
        assertNotNull(todoModified);
        assertNull(todoModified.getDeadline());
    }

    @DisplayName("ÉTANT DONNÉ QUE je fournis une date d'échéance dans le passé, LORSQUE je tente de la définir, ALORS j'obtiens un avertissement mais la date est acceptée")
    @Test
    void testPastDeadline() {
        LocalDate date = LocalDate.now().minusDays(7);
        TodoInputDTO updateDTO = TodoInputDTO.builder()
                .title("Toto title")
                .deadline(date)
                .build();

        ResponseEntity<?> response = controller.updateTodo(99L, updateDTO);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        TodoDTO todoModified = (TodoDTO) response.getBody();

        assertNotNull(todoModified);
        assertNotNull(todoModified.getDeadline());
        assertEquals("Deadline passed", todoModified.getMessage());
        assertEquals(date, todoModified.getDeadline());
    }

    @DisplayName("ÉTANT DONNÉ QUE je tente de définir une échéance sur une tâche inexistante, LORSQUE j'utilise un ID invalide, ALORS j'obtiens une erreur \"Task not found\"")
    @Test
    void testDeadlineOnNonExistentTask() {
        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("validTitle")
                .deadline(LocalDate.now().plusDays(7))
                .build();

        assertThrows(TaskNotFound.class, () -> controller.updateTodo(999L, inputDTO));
    }
}
