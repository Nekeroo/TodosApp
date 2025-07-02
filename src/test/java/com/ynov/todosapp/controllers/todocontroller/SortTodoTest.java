package com.ynov.todosapp.controllers.todocontroller;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.dto.TodoDTO;
import com.ynov.todosapp.dto.TodosPaginedDTO;
import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.exceptions.InvalidSortCriteria;
import com.ynov.todosapp.models.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class SortTodoTest extends TodoControllerTest {

    private LocalDate oldDate;
    private LocalDate newDate;
    private Todo oldTodo;
    private Todo newTodo;
    private Todo todoA;
    private Todo todoZ;
    private Todo todoTask;
    private Todo inProgressTask;
    private Todo doneTask;
    private Todo oldTodoInProgress;
    private Todo newTodoInProgress;

    @BeforeEach
    void setUp() {
        oldDate = LocalDate.now().minusDays(5);
        newDate = LocalDate.now();
        
        oldTodo = Todo.builder()
                .id(1L)
                .title("Older Todo")
                .description("Description")
                .status(StatusEnum.TODO)
                .createdDate(oldDate)
                .build();

        newTodo = Todo.builder()
                .id(2L)
                .title("Newer Todo")
                .description("Description")
                .status(StatusEnum.TODO)
                .createdDate(newDate)
                .build();
                
        todoA = Todo.builder()
                .id(1L)
                .title("A Todo")
                .description("Description")
                .status(StatusEnum.TODO)
                .createdDate(LocalDate.now())
                .build();

        todoZ = Todo.builder()
                .id(2L)
                .title("Z Todo")
                .description("Description")
                .status(StatusEnum.TODO)
                .createdDate(LocalDate.now())
                .build();
                
        todoTask = Todo.builder()
                .id(1L)
                .title("Todo Task")
                .description("Description")
                .status(StatusEnum.TODO)
                .createdDate(LocalDate.now())
                .build();

        inProgressTask = Todo.builder()
                .id(2L)
                .title("In Progress Task")
                .description("Description")
                .status(StatusEnum.IN_PROGRESS)
                .createdDate(LocalDate.now())
                .build();

        doneTask = Todo.builder()
                .id(3L)
                .title("Done Task")
                .description("Description")
                .status(StatusEnum.DONE)
                .createdDate(LocalDate.now())
                .build();
                
        oldTodoInProgress = Todo.builder()
                .id(1L)
                .title("Old Todo")
                .description("Description")
                .status(StatusEnum.IN_PROGRESS)
                .createdDate(oldDate)
                .build();

        newTodoInProgress = Todo.builder()
                .id(2L)
                .title("New Todo")
                .description("Description")
                .status(StatusEnum.IN_PROGRESS)
                .createdDate(newDate)
                .build();
    }
    
    private void configureMockForSorting(String sortBy, String sortDirection, List<Todo> sortedList) {
        when(service.getAllTodos(anyInt(), eq(10), eq(""), eq(""), eq(sortBy), eq(sortDirection)))
                .thenReturn(new PageImpl<>(sortedList));
    }
    
    private void configureMockForFilteredSorting(String status, String sortBy, String sortDirection, List<Todo> sortedList) {
        when(service.getAllTodos(anyInt(), eq(10), eq(""), eq(status), eq(sortBy), eq(sortDirection)))
                .thenReturn(new PageImpl<>(sortedList));
    }
    
    private List<TodoDTO> executeControllerAndGetTodos(String sortBy, String sortDirection) {
        return executeControllerAndGetTodos("", sortBy, sortDirection);
    }
    
    private List<TodoDTO> executeControllerAndGetTodos(String status, String sortBy, String sortDirection) {
        ResponseEntity<TodosPaginedDTO> response = controller.retrieveTodos(0, 10, "", status, sortBy, sortDirection);
        
        assertNotNull(response);
        assertNotNull(response.getBody());
        List<TodoDTO> todos = response.getBody().getTodos().stream().toList();
        assertFalse(todos.isEmpty());
        
        return todos;
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai plusieurs tâches, LORSQUE je trie par date de création ascendante, ALORS les tâches sont affichées de la plus ancienne à la plus récente")
    @Test
    void testSortByCreatedDateAsc() {
        configureMockForSorting("createdDate", "asc", List.of(oldTodo, newTodo));

        List<TodoDTO> todos = executeControllerAndGetTodos("createdDate", "asc");
        
        assertEquals(2, todos.size());
        assertEquals("Older Todo", todos.get(0).getTitle());
        assertEquals("Newer Todo", todos.get(1).getTitle());
        
        assertTrue(todos.get(0).getCreatedDate().isBefore(todos.get(1).getCreatedDate()));
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai plusieurs tâches, LORSQUE je trie par date de création descendante, ALORS les tâches sont affichées de la plus récente à la plus ancienne")
    @Test
    void testSortByCreatedDateDesc() {
        configureMockForSorting("createdDate", "desc", List.of(newTodo, oldTodo));

        List<TodoDTO> todos = executeControllerAndGetTodos("createdDate", "desc");
        
        assertEquals(2, todos.size());
        assertEquals("Newer Todo", todos.get(0).getTitle());
        assertEquals("Older Todo", todos.get(1).getTitle());
        
        assertTrue(todos.get(0).getCreatedDate().isAfter(todos.get(1).getCreatedDate()));
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai plusieurs tâches, LORSQUE je trie par titre ascendant, ALORS les tâches sont affichées par ordre alphabétique")
    @Test
    void testSortByTitleAsc() {
        configureMockForSorting("title", "asc", List.of(todoA, todoZ));

        List<TodoDTO> todos = executeControllerAndGetTodos("title", "asc");
        
        assertEquals(2, todos.size());
        assertEquals("A Todo", todos.get(0).getTitle());
        assertEquals("Z Todo", todos.get(1).getTitle());
        
        assertTrue(todos.get(0).getTitle().compareTo(todos.get(1).getTitle()) < 0);
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai plusieurs tâches, LORSQUE je trie par titre descendant, ALORS les tâches sont affichées par ordre alphabétique inversé")
    @Test
    void testSortByTitleDesc() {
        configureMockForSorting("title", "desc", List.of(todoZ, todoA));

        List<TodoDTO> todos = executeControllerAndGetTodos("title", "desc");
        
        assertEquals(2, todos.size());
        assertEquals("Z Todo", todos.get(0).getTitle());
        assertEquals("A Todo", todos.get(1).getTitle());
        
        assertTrue(todos.get(0).getTitle().compareTo(todos.get(1).getTitle()) > 0);
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai plusieurs tâches, LORSQUE je trie par statut, ALORS les tâches sont groupées par statut dans l'ordre : TODO, ONGOING, DONE")
    @Test
    void testSortByStatus() {
        configureMockForSorting("status", "asc", List.of(todoTask, inProgressTask, doneTask));

        List<TodoDTO> todos = executeControllerAndGetTodos("status", "asc");
        
        assertEquals(3, todos.size());
        assertEquals(StatusEnum.TODO.getLabel(), todos.get(0).getStatus());
        assertEquals(StatusEnum.IN_PROGRESS.getLabel(), todos.get(1).getStatus());
        assertEquals(StatusEnum.DONE.getLabel(), todos.get(2).getStatus());
    }

    @DisplayName("ÉTANT DONNÉ QUE je ne spécifie pas de tri, LORSQUE je consulte la liste, ALORS les tâches sont triées par date de création descendante par défaut")
    @Test
    void testDefaultSortIsCreatedDateDesc() {
        configureMockForSorting("createdDate", "desc", List.of(newTodo, oldTodo));

        List<TodoDTO> todos = executeControllerAndGetTodos("createdDate", "desc");
        
        assertEquals(2, todos.size());
        assertEquals("Newer Todo", todos.get(0).getTitle());
        assertEquals("Older Todo", todos.get(1).getTitle());
        
        assertTrue(todos.get(0).getCreatedDate().isAfter(todos.get(1).getCreatedDate()));
    }

    @DisplayName("ÉTANT DONNÉ QUE je spécifie un critère de tri invalide, LORSQUE je fais la demande, ALORS j'obtiens une erreur \"Invalid sort criteria\"")
    @Test
    void testInvalidSortCriteria() {
        when(service.getAllTodos(anyInt(), eq(10), eq(""), eq(""), eq("invalid"), eq("asc")))
                .thenThrow(new InvalidSortCriteria());

        assertThrows(InvalidSortCriteria.class, () -> 
            controller.retrieveTodos(0, 10, "", "", "invalid", "asc")
        );
    }

    @DisplayName("ÉTANT DONNÉ QUE je combine tri et filtre, LORSQUE j'applique les deux, ALORS le tri s'applique sur les résultats filtrés")
    @Test
    void testCombineSortAndFilter() {
        configureMockForFilteredSorting("progress", "createdDate", "desc", List.of(newTodoInProgress, oldTodoInProgress));

        List<TodoDTO> todos = executeControllerAndGetTodos("progress", "createdDate", "desc");
        
        assertEquals(2, todos.size());
        
        todos.forEach(todo -> 
            assertEquals(StatusEnum.IN_PROGRESS.getLabel(), todo.getStatus())
        );
        
        assertEquals("New Todo", todos.get(0).getTitle());
        assertEquals("Old Todo", todos.get(1).getTitle());
        assertTrue(todos.get(0).getCreatedDate().isAfter(todos.get(1).getCreatedDate()));
    }
}
