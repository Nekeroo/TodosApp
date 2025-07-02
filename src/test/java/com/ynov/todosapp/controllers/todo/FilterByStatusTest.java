package com.ynov.todosapp.controllers.todocontroller;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.dto.TodosPaginedDTO;
import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.exceptions.todo.InvalidFilterStatus;
import com.ynov.todosapp.models.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class FilterByStatusTest extends TodoControllerTest {
    
    private Todo inProgressTodo;
    
    @BeforeEach
    void setUp() {
        inProgressTodo = Todo.builder()
                .id(2L)
                .title("Todo Title")
                .description("Todo Description")
                .status(StatusEnum.IN_PROGRESS)
                .createdDate(creationDate)
                .build();
    }
    
    private void configureMockForStatus(String status, List<Todo> todos) {
        when(service.getAllTodos(anyInt(), eq(10), eq(""), eq(status), eq(""), eq("")))
                .thenReturn(new PageImpl<>(todos));
    }
    
    private void configureMockForInvalidStatus(String status) {
        when(service.getAllTodos(anyInt(), eq(10), eq(""), eq(status), eq(""), eq("")))
                .thenThrow(new InvalidFilterStatus());
    }
    
    private ResponseEntity<TodosPaginedDTO> executeFilterByStatus(String status) {
        return controller.retrieveTodos(0, 10, "", status, "", "");
    }
    
    @DisplayName("ÉTANT DONNÉ QUE j'ai des tâches avec différents statuts, LORSQUE je filtre par \"TODO\", \"ONGOING\" ou \"DONE\", ALORS seules les tâches avec le statut correspondant sont retournées")
    @Test
    void testFilterByStatus() {
        configureMockForStatus("progress", List.of(inProgressTodo));

        ResponseEntity<TodosPaginedDTO> response = executeFilterByStatus("progress");

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getTodos().isEmpty());

        response.getBody().getTodos().forEach(todo -> {
            assertEquals(StatusEnum.IN_PROGRESS.getLabel(), todo.getStatus());
        });
    }

    @DisplayName("ÉTANT DONNÉ QUE je filtre par un statut et qu'aucune tâche ne correspond, LORSQUE j'applique le filtre, ALORS j'obtiens une liste vide")
    @Test
    void testFilterByStatusEmptyList() {
        configureMockForStatus("progress", List.of());
        
        ResponseEntity<TodosPaginedDTO> response = executeFilterByStatus("progress");
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getTodos().isEmpty());
    }

    @DisplayName("ÉTANT DONNÉ QUE je filtre par un statut invalide, LORSQUE j'applique le filtre, ALORS j'obtiens une erreur \"Invalid filter status\"")
    @Test
    void testFilterByStatusInvalidStatus() {
        configureMockForInvalidStatus("invalid");
        assertThrows(InvalidFilterStatus.class, () -> executeFilterByStatus("invalid"));
    }
}
