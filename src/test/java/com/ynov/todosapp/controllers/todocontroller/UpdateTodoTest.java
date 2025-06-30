package com.ynov.todosapp.controllers.todocontroller;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.dto.TodoDTO;
import com.ynov.todosapp.dto.input.TodoInputDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UpdateTodoTest extends TodoControllerTest {

    @DisplayName("ÉTANT DONNÉ QUE j'ai une tâche existante, LORSQUE je modifie son titre avec une valeur valide, ALORS le nouveau titre est sauvegardé et les autres champs restent inchangés\n")
    @Test
    void testUpdateOneTodo() {

        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("Todo title different")
                .build();

        ResponseEntity<?> response = controller.updateTodo(1L, inputDTO);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        TodoDTO todoDTO = (TodoDTO) response.getBody();

        assertNotNull(todoDTO);
        assertSame(inputDTO.getTitle(), todoDTO.getTitle());
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai une tâche existante, LORSQUE je modifie sa description avec une valeur valide, ALORS la nouvelle description est sauvegardée et les autres champs restent inchangés\n")
    @Test
    void testUpdateOneTodoWithDescription() {

        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("Toto title")
                .description("Todo description different")
                .build();

        ResponseEntity<?> response = controller.updateTodo(1L, inputDTO);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        TodoDTO todoDTO = (TodoDTO) response.getBody();

        assertNotNull(todoDTO);
        assertSame(inputDTO.getDescription(), todoDTO.getDescription());
    }

    @DisplayName("ÉTANT DONNÉ QUE je tente de modifier le titre d'une tâche avec une valeur vide, LORSQUE je soumets la modification, ALORS j'obtiens une erreur \"Title is required\"\n")
    @Test
    void testUpdateOneTodoWithEmptyTitle() {
        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .build();

        ResponseEntity<?> response = controller.updateTodo(1L, inputDTO);

        assertTrue(response.getStatusCode().is4xxClientError());

        String body = (String) response.getBody();

        assertEquals("Title is required", body);
    }

    @DisplayName("ÉTANT DONNÉ QUE je tente de modifier une tâche avec un titre de plus de 100 caractères, LORSQUE je soumets, ALORS j'obtiens une erreur 'Title cannot exceed 100 characters'")
    @Test
    void testUpdateOneTodoWithTitleOver100Caracters() {
        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("e".repeat(101))
                .build();

        ResponseEntity<?> response = controller.updateTodo(1L, inputDTO);

        assertTrue(response.getStatusCode().is4xxClientError());

        String body = (String) response.getBody();

        assertEquals("Title cannot exceed 100 characters", body);
    }

    @DisplayName("ÉTANT DONNÉ QUE je tente de modifier une tâche avec une description de plus de 500 caractères, LORSQUE je soumets, ALORS j'obtiens une erreur \"Description cannot exceed 500 characters\"\n")
    @Test
    void testUpdateOneTodoWithDescriptionOver500Caracters() {
        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("validTitle")
                .description("e".repeat(501))
                .build();

        ResponseEntity<?> response = controller.updateTodo(1L, inputDTO);

        assertTrue(response.getStatusCode().is4xxClientError());

        String body = (String) response.getBody();

        assertEquals("Description cannot exceed 500 characters", body);
    }

    @DisplayName("ÉTANT DONNÉ QUE je tente de modifier une tâche inexistante, LORSQUE j'utilise un ID invalide, ALORS j'obtiens une erreur \"Task not found\"\n")
    @Test
    void testUpdateOneTodoWithIdNotFound() {
        when(service.getTodoById(1L)).thenReturn(Optional.empty());

        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("validTitle")
                .description("Description updated")
                .build();

        ResponseEntity<?> response = controller.updateTodo(1L, inputDTO);

        assertTrue(response.getStatusCode().is4xxClientError());

        String body = (String) response.getBody();

        assertEquals("Task not found", body);
    }

    @DisplayName("ÉTANT DONNÉ QUE je tente de modifier une tâche inexistante, LORSQUE j'utilise un ID invalide, ALORS j'obtiens une erreur \"Task not found\"\n")
    @Test
    void testUpdateOneTodoWithFieldsNotUpdatable() {
        when(service.getTodoById(1L)).thenReturn(Optional.empty());

        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("validTitle")
                .description("Different description")
                .build();

        ResponseEntity<?> response = controller.updateTodo(1L, inputDTO);

        assertTrue(response.getStatusCode().is4xxClientError());

        String body = (String) response.getBody();

        assertEquals("Task not found", body);
    }

}
