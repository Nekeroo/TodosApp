package com.ynov.todosapp.controllers.todo;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.dto.TodoDTO;
import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.exceptions.todo.InvalidIDFormat;
import com.ynov.todosapp.exceptions.todo.TaskNotFound;
import com.ynov.todosapp.models.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RetrieveTodoTest extends TodoControllerTest {


    @DisplayName("ÉTANT DONNÉ QUE j'ai une tâche existante avec ID valide, LORSQUE je consulte cette tâche, ALORS j'obtiens tous ses détails : ID, titre, description, statut, date de création, etc..")
    @Test
    void testGetOneTodo() {

        Todo compareTodo = Todo.builder()
                .id(99L)
                .title("toto")
                .description("toto is toto !")
                .status(StatusEnum.TODO)
                .createdDate(LocalDate.of(2025, 06, 30))
                .build();

        ResponseEntity<?> repsonse = controller.retrieveTodoById("99");

        assertTrue(repsonse.getStatusCode().is2xxSuccessful());

        TodoDTO todoDTO = (TodoDTO) repsonse.getBody();
        assertNotNull(todoDTO);
        assertAll(() -> {
            assertEquals(compareTodo.getTitle(), todoDTO.getTitle());
            assertEquals(compareTodo.getDescription(), todoDTO.getDescription());
            assertEquals(compareTodo.getCreatedDate(), todoDTO.getCreatedDate());
            assertEquals(compareTodo.getStatus().getLabel(), todoDTO.getStatus());
        });
    }

    @DisplayName("ÉTANT DONNÉ QUE je consulte une tâche avec un ID inexistant, LORSQUE je fais la demande, ALORS j'obtiens une erreur \"Task not found\" avec, si web, le code 404\n")
    @Test
    void testGetOneTodoNotFound() {

        assertThrows(TaskNotFound.class, () -> controller.retrieveTodoById("1"));
    }

    @DisplayName("ÉTANT DONNÉ QUE je consulte une tâche avec un ID au mauvais format, LORSQUE je fais la demande, ALORS j'obtiens une erreur \"Invalid ID format\"\n")
    @Test
    void testGetOneTodoWithIdWithIncorrectFormat() {
        assertThrows(InvalidIDFormat.class, () -> controller.retrieveTodoById("azerty"));
    }
}
