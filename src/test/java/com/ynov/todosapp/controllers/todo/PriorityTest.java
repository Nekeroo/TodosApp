package com.ynov.todosapp.controllers.todo;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.dto.TodoDTO;
import com.ynov.todosapp.dto.TodosPaginedDTO;
import com.ynov.todosapp.dto.input.TodoInputDTO;
import com.ynov.todosapp.enums.PriorityEnum;
import com.ynov.todosapp.exceptions.priority.InvalidPriorityException;
import com.ynov.todosapp.models.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PriorityTest extends TodoControllerTest {

    @DisplayName("ÉTANT DONNÉ QUE j'ai des tâches avec différentes priorités, LORSQUE je trie par priorité, ALORS elles sont ordonnées : MAJOR, HIGH, NORMAL, LOW")
    @Test
    void testSortByPriority() {
        ResponseEntity<TodosPaginedDTO> response = controller.retrieveTodos(0, 10, "", "", "priority", "asc", null, null, "");

        assertNotNull(response);
        assertNotNull(response.getBody());

        List<TodoDTO> todos = response.getBody().getTodos().stream().toList();
        List<TodoDTO> sortedTodos = new ArrayList<>(response.getBody().getTodos());
        sortedTodos.sort(Comparator.comparing(TodoDTO::getPriority));

        assertFalse(todos.isEmpty());
        
        assertEquals(todos, sortedTodos);
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai des tâches avec différentes priorités, LORSQUE je filtre par une priorité spécifique, ALORS seules les tâches avec cette priorité sont retournées")
    @Test
    void testFilterByPriority() {
        ResponseEntity<TodosPaginedDTO> response = controller.retrieveTodos(0, 10, "", "", "createdDate", "desc", null, null, "high");
        
        assertNotNull(response);
        assertNotNull(response.getBody());
        List<TodoDTO> todos = response.getBody().getTodos().stream().toList();
        assertFalse(todos.isEmpty());
        
        todos.forEach(todo -> {
            assertEquals(PriorityEnum.HIGH.getLabel(), todo.getPriority());
        });
    }

    @DisplayName("- **ÉTANT DONNÉ QUE** j'ai une tâche existante, **LORSQUE** je définis sa priorité à \"LOW\", \"NORMAL\", \"HIGH\" ou \"CRITICAL\", **ALORS** la priorité est enregistrée\n")
    @Test
    void testUpdateOneTodoWithPriority() {

        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("toto")
                .description("toto is toto")
                .priority(PriorityEnum.HIGH.getLabel())
                .build();

        Todo todo = service.getTodoById(99L);

        assertEquals(PriorityEnum.LOW, todo.getPriority());

        ResponseEntity<?> response = controller.updateTodo(99L, inputDTO);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        TodoDTO todoModified = (TodoDTO) response.getBody();

        assertNotNull(todoModified);
        assertEquals(PriorityEnum.HIGH.getLabel(), todoModified.getPriority());
    }

    @DisplayName("- **ÉTANT DONNÉ QUE** je crée une tâche sans spécifier de priorité, **LORSQUE** je consulte ses détails, **ALORS** sa priorité par défaut est \"NORMAL\"\n")
    @Test
    void testUpdateOneTodoWithEmptyPriority() {

        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("toto")
                .description("toto is toto")
                .build();

        ResponseEntity<?> response = controller.createTodo(inputDTO);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        TodoDTO todo = (TodoDTO) response.getBody();

        assertNotNull(todo);
        assertEquals(PriorityEnum.NORMAL.getLabel(), todo.getPriority());
    }

    @DisplayName("- **ÉTANT DONNÉ QUE** je tente de définir une priorité invalide, **LORSQUE** je soumets la modification, **ALORS** j'obtiens une erreur \"Invalid priority. Allowed values: LOW, NORMAL, HIGH, CRITICAL\"\n")
    @Test
    void testUpdateOneTodoWithInvalidPriority() {

        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("toto")
                .description("toto is toto")
                .priority("INVALID_PRIORITY")
                .build();

        assertThrows(InvalidPriorityException.class, () -> controller.updateTodo(99L, inputDTO));
    }
}
