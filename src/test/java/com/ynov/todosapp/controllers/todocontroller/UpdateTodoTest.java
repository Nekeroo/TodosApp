package com.ynov.todosapp.controllers.todocontroller;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.dto.TodoDTO;
import com.ynov.todosapp.dto.input.TodoInputDTO;
import com.ynov.todosapp.exceptions.DescriptionIsTooLong;
import com.ynov.todosapp.exceptions.TaskNotFound;
import com.ynov.todosapp.exceptions.TitleIsRequired;
import com.ynov.todosapp.exceptions.TitleIsTooLong;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

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

        assertThrows(TitleIsRequired.class, () -> controller.updateTodo(1L, inputDTO));
    }

    @DisplayName("ÉTANT DONNÉ QUE je tente de modifier une tâche avec un titre de plus de 100 caractères, LORSQUE je soumets, ALORS j'obtiens une erreur 'Title cannot exceed 100 characters'")
    @Test
    void testUpdateOneTodoWithTitleOver100Caracters() {
        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("e".repeat(101))
                .build();

        assertThrows(TitleIsTooLong.class, () -> controller.updateTodo(1L, inputDTO));
    }

    @DisplayName("ÉTANT DONNÉ QUE je tente de modifier une tâche avec une description de plus de 500 caractères, LORSQUE je soumets, ALORS j'obtiens une erreur \"Description cannot exceed 500 characters\"\n")
    @Test
    void testUpdateOneTodoWithDescriptionOver500Caracters() {
        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("validTitle")
                .description("e".repeat(501))
                .build();

        assertThrows(DescriptionIsTooLong.class, () -> controller.updateTodo(1L, inputDTO));
    }

    @DisplayName("ÉTANT DONNÉ QUE je tente de modifier une tâche inexistante, LORSQUE j'utilise un ID invalide, ALORS j'obtiens une erreur \"Task not found\"\n")
    @Test
    void testUpdateOneTodoWithIdNotFound() {
        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("validTitle")
                .description("Description updated")
                .build();

        when(service.updateTodo(eq(1L), any(TodoInputDTO.class))).thenReturn(null);

        assertThrows(TaskNotFound.class, () -> controller.updateTodo(1L, inputDTO));
    }

    @DisplayName("ÉTANT DONNÉ QUE je tente de modifier une tâche inexistante, LORSQUE j'utilise un ID invalide, ALORS j'obtiens une erreur \"Task not found\"\n")
    @Test
    void testUpdateOneTodoWithFieldsNotUpdatable() {

        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("validTitle")
                .description("Different description")
                .build();

        when(service.updateTodo(eq(1L), any(TodoInputDTO.class))).thenReturn(null);
        assertThrows(TaskNotFound.class, () -> controller.updateTodo(1L, inputDTO));
    }

}
