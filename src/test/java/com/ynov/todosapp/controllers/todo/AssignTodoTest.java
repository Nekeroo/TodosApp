package com.ynov.todosapp.controllers.todo;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.dto.TodoAssignInputDTO;
import com.ynov.todosapp.dto.TodoDTO;
import com.ynov.todosapp.exceptions.todo.TaskNotFound;
import com.ynov.todosapp.exceptions.user.UserNotFound;
import com.ynov.todosapp.models.Todo;
import com.ynov.todosapp.services.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class AssignTodoTest extends TodoControllerTest {

    @Autowired
    private TodoService todoService;

    @DisplayName("ÉTANT DONNÉ QUE j'ai une tâche existante et un utilisateur existant, LORSQUE j'assigne la tâche à l'utilisateur, ALORS l'assignation est enregistrée et visible dans les détails de la tâche\n")
    @Test
    void testAssignWithAValidTodoANdAValidUser() {

        TodoAssignInputDTO todoAssignInputDTO = TodoAssignInputDTO.builder().idUser(101L).shouldAssign(true).build();

        ResponseEntity<?> responseEntity = controller.assignUserToTodo(103L, todoAssignInputDTO);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        TodoDTO todo = (TodoDTO) responseEntity.getBody();

        assertAll(() -> {
            assertNotNull(todo);
            assertNotNull(todo.getId());
            assertEquals(todoAssignInputDTO.getIdUser(), todo.getUserAffected());
        });
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai une tâche déjà assignée, LORSQUE je l'assigne à un autre utilisateur, ALORS l'ancienne assignation est remplacée par la nouvelle\n")
    @Test
    void testAssignWithAValidTodoAndAValidUser() {

        Todo existingTodo = todoService.getTodoById(99L);

        assertNotNull(existingTodo.getUserAffected());

        TodoAssignInputDTO todoAssignInputDTO = TodoAssignInputDTO.builder().idUser(101L).shouldAssign(true).build();

        ResponseEntity<?> responseEntity = controller.assignUserToTodo(99L, todoAssignInputDTO);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        TodoDTO todo = (TodoDTO) responseEntity.getBody();

        assertAll(() -> {
            assertNotNull(todo);
            assertNotNull(todo.getId());
            assertEquals(todoAssignInputDTO.getIdUser(), todo.getUserAffected());
        });
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai une tâche assignée, LORSQUE je la désassigne (assigner à null/vide), ALORS la tâche n'est plus assignée à personne\n")
    @Test
    void testUnassignWithAValidTodo() {

        TodoAssignInputDTO todoAssignInputDTO = TodoAssignInputDTO.builder().idUser(null).shouldAssign(false).build();

        ResponseEntity<?> responseEntity = controller.assignUserToTodo(99L, todoAssignInputDTO);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        TodoDTO todo = (TodoDTO) responseEntity.getBody();

        assertAll(() -> {
            assertNotNull(todo);
            assertNotNull(todo.getId());
            assertNull(todo.getUserAffected());
        });

    }

    @DisplayName("ÉTANT DONNÉ QUE je tente d'assigner une tâche à un utilisateur inexistant, LORSQUE j'utilise un ID utilisateur invalide, ALORS j'obtiens une erreur \"User not found\"\n")
    @Test
    void testAssignWithAnInvalidUser() {

        TodoAssignInputDTO todoAssignInputDTO = TodoAssignInputDTO.builder().idUser(999L).shouldAssign(true).build();

        assertThrows(UserNotFound.class, () -> controller.assignUserToTodo(99L, todoAssignInputDTO));
    }

    @DisplayName("ÉTANT DONNÉ QUE je tente d'assigner une tâche inexistante, LORSQUE j'utilise un ID de tâche invalide, ALORS j'obtiens une erreur \"Task not found\"\n")
    @Test
    void testAssignWithAnInvalidTask() {

        TodoAssignInputDTO todoAssignInputDTO = TodoAssignInputDTO.builder().idUser(101L).shouldAssign(true).build();

        assertThrows(TaskNotFound.class, () -> controller.assignUserToTodo(999L, todoAssignInputDTO));
    }


}
