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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DeleteTodoTest extends TodoControllerTest {

    @DisplayName("ÉTANT DONNÉ QUE j'ai une tâche existante, LORSQUE je la supprime, ALORS elle n'apparaît plus dans la liste des tâches\n")
    @Test
    void testDeleteOneTodoWithValidId() {
        ResponseEntity<?> response = controller.deleteTodo(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        when(repository.findAll()).thenReturn(savedTodos);

        ResponseEntity<?> responseGet = controller.retrieveTodos();

        List<TodoDTO> todos = (List<TodoDTO>) responseGet.getBody();

        assertTrue(todos.isEmpty());
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai supprimé une tâche, LORSQUE je tente de la consulter, de la supprimer, de la modifier ou de change son status par son ID, ALORS j'obtiens une erreur \"Task not found\"\n")
    @Test
    void testDeleteOneTodoWithInvalidId() {
        when(service.getTodoById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<?> responseGet = controller.retrieveTodoById("1");

        assertTrue(responseGet.getStatusCode().is4xxClientError());

        String body = (String) responseGet.getBody();

        assertEquals("Task not found", body);
    }
}
