package com.ynov.todosapp.controllers.todocontroller;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.dto.TodoDTO;
import com.ynov.todosapp.dto.TodosPaginedDTO;
import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.exceptions.todo.InvalidSortCriteria;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SortTodoTest extends TodoControllerTest {

    @DisplayName("ÉTANT DONNÉ QUE j'ai plusieurs tâches, LORSQUE je trie par date de création ascendante, ALORS les tâches sont affichées de la plus ancienne à la plus récente")
    @Test
    void testSortByCreatedDateAsc() {

        ResponseEntity<?> todos = controller.retrieveTodos(0, 10, "", "", "createdDate", "asc");

        TodosPaginedDTO todosPaginedDTO = (TodosPaginedDTO) todos.getBody();

        List<TodoDTO> todosRetrieved = todosPaginedDTO.getTodos().stream().toList();

        assertEquals(4, todosRetrieved.size());
        assertEquals("titi", todosRetrieved.get(0).getTitle());
        assertEquals("tete", todosRetrieved.get(1).getTitle());
        
        assertTrue(todosRetrieved.get(0).getCreatedDate().isBefore(todosRetrieved.get(1).getCreatedDate()));
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai plusieurs tâches, LORSQUE je trie par date de création descendante, ALORS les tâches sont affichées de la plus récente à la plus ancienne")
    @Test
    void testSortByCreatedDateDesc() {

        ResponseEntity<?> todos = controller.retrieveTodos(0, 10, "", "", "createdDate", "");

        TodosPaginedDTO todosPaginedDTO = (TodosPaginedDTO) todos.getBody();

        List<TodoDTO> todosRetrieved = todosPaginedDTO.getTodos().stream().toList();

        System.out.println(todosRetrieved);

        assertEquals(4, todosRetrieved.size());
        assertEquals("toto", todosRetrieved.get(0).getTitle());
        assertEquals("tata", todosRetrieved.get(1).getTitle());

        assertTrue(todosRetrieved.get(0).getCreatedDate().isAfter(todosRetrieved.get(1).getCreatedDate()));
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai plusieurs tâches, LORSQUE je trie par titre ascendant, ALORS les tâches sont affichées par ordre alphabétique")
    @Test
    void testSortByTitleAsc() {

        ResponseEntity<?> todos = controller.retrieveTodos(0, 10, "", "", "title", "asc");

        TodosPaginedDTO todosPaginedDTO = (TodosPaginedDTO) todos.getBody();

        List<TodoDTO> todosRetrieved = todosPaginedDTO.getTodos().stream().toList();

        assertEquals(4, todosRetrieved.size());
        assertEquals("tata", todosRetrieved.get(0).getTitle());
        assertEquals("tete", todosRetrieved.get(1).getTitle());

        assertTrue(todosRetrieved.get(0).getTitle().compareTo(todosRetrieved.get(1).getTitle()) < 0);
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai plusieurs tâches, LORSQUE je trie par titre descendant, ALORS les tâches sont affichées par ordre alphabétique inversé")
    @Test
    void testSortByTitleDesc() {

        ResponseEntity<?> todos = controller.retrieveTodos(0, 10, "", "", "title", "desc");

        TodosPaginedDTO todosPaginedDTO = (TodosPaginedDTO) todos.getBody();

        List<TodoDTO> todosRetrieved = todosPaginedDTO.getTodos().stream().toList();

        assertEquals(4, todosRetrieved.size());
        assertEquals("toto", todosRetrieved.get(0).getTitle());
        assertEquals("titi", todosRetrieved.get(1).getTitle());
        
        assertTrue(todosRetrieved.get(0).getTitle().compareTo(todosRetrieved.get(1).getTitle()) > 0);
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai plusieurs tâches, LORSQUE je trie par statut, ALORS les tâches sont groupées par statut dans l'ordre : TODO, ONGOING, DONE")
    @Test
    void testSortByStatus() {

        ResponseEntity<?> todos = controller.retrieveTodos(0, 10, "", "", "status", "asc");

        TodosPaginedDTO todosPaginedDTO = (TodosPaginedDTO) todos.getBody();

        List<TodoDTO> todosRetrieved = todosPaginedDTO.getTodos().stream().toList();

        assertEquals(4, todosRetrieved.size());
        assertEquals(StatusEnum.DONE.getLabel(), todosRetrieved.get(0).getStatus());
        assertEquals(StatusEnum.IN_PROGRESS.getLabel(), todosRetrieved.get(1).getStatus());
        assertEquals(StatusEnum.IN_PROGRESS.getLabel(), todosRetrieved.get(2).getStatus());
        assertEquals(StatusEnum.TODO.getLabel(), todosRetrieved.get(3).getStatus());
    }

    @DisplayName("ÉTANT DONNÉ QUE je ne spécifie pas de tri, LORSQUE je consulte la liste, ALORS les tâches sont triées par date de création descendante par défaut")
    @Test
    void testDefaultSortIsCreatedDateDesc() {

        ResponseEntity<?> todos = controller.retrieveTodos(0, 10, "", "", "createdDate", "desc");

        TodosPaginedDTO todosPaginedDTO = (TodosPaginedDTO) todos.getBody();

        List<TodoDTO> todosRetrieved = todosPaginedDTO.getTodos().stream().toList();

        assertEquals(4, todosRetrieved.size());
        assertEquals("toto", todosRetrieved.get(0).getTitle());
        assertEquals("tata", todosRetrieved.get(1).getTitle());
        
        assertTrue(todosRetrieved.get(0).getCreatedDate().isAfter(todosRetrieved.get(1).getCreatedDate()));
    }

    @DisplayName("ÉTANT DONNÉ QUE je spécifie un critère de tri invalide, LORSQUE je fais la demande, ALORS j'obtiens une erreur \"Invalid sort criteria\"")
    @Test
    void testInvalidSortCriteria() {
        assertThrows(InvalidSortCriteria.class, () -> 
            controller.retrieveTodos(0, 10, "", "", "invalid", "asc")
        );
    }

    @DisplayName("ÉTANT DONNÉ QUE je combine tri et filtre, LORSQUE j'applique les deux, ALORS le tri s'applique sur les résultats filtrés")
    @Test
    void testCombineSortAndFilter() {

        ResponseEntity<?> todos = controller.retrieveTodos(0, 10, "", "progress", "createdDate", "desc");

        TodosPaginedDTO todosPaginedDTO = (TodosPaginedDTO) todos.getBody();

        List<TodoDTO> todosRetrieved = todosPaginedDTO.getTodos().stream().toList();

        assertEquals(2, todosRetrieved.size());

        todosRetrieved.forEach(todo ->
            assertEquals(StatusEnum.IN_PROGRESS.getLabel(), todo.getStatus())
        );
        
        assertEquals("tata", todosRetrieved.get(0).getTitle());
        assertEquals("tete", todosRetrieved.get(1).getTitle());
        assertTrue(todosRetrieved.get(0).getCreatedDate().isAfter(todosRetrieved.get(1).getCreatedDate()));
    }
}
