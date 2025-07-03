package com.ynov.todosapp.controllers.todo;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.dto.TodosPaginedDTO;
import com.ynov.todosapp.exceptions.todo.TaskNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteTodoTest extends TodoControllerTest {

    @DisplayName("ÉTANT DONNÉ QUE j'ai une tâche existante, LORSQUE je la supprime, ALORS elle n'apparaît plus dans la liste des tâches\n")
    @Test
    void testDeleteOneTodoWithValidId() {
        ResponseEntity<?> response = controller.deleteTodo(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        ResponseEntity<?> responseGet = controller.retrieveTodos(1, 10, "", "", "createdDate", "");

        TodosPaginedDTO todos = (TodosPaginedDTO) responseGet.getBody();

        assertNotNull(todos.getTodos());
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai supprimé une tâche, LORSQUE je tente de la consulter, de la supprimer, de la modifier ou de change son status par son ID, ALORS j'obtiens une erreur \"Task not found\"\n")
    @Test
    void testDeleteOneTodoWithInvalidId() {
        assertThrows(TaskNotFound.class, () -> controller.retrieveTodoById("2"));
    }
}
