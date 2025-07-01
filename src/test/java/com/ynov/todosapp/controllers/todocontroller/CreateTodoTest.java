package com.ynov.todosapp.controllers.todocontroller;

import com.ynov.todosapp.controllers.TodoControllerTest;
import com.ynov.todosapp.dto.TodoDTO;
import com.ynov.todosapp.dto.input.TodoInputDTO;
import com.ynov.todosapp.enums.StatusEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CreateTodoTest extends TodoControllerTest {

    @DisplayName("ÉTANT DONNÉ QUE je fournis un titre valide (non vide, maximum 100 caractères), LORSQUE je crée une tâche, ALORS elle est créée avec un ID unique, le titre fourni, une description vide par défaut, une date de création et le statut \"TODO\"")
    @Test
    void testCreateTodoWithValidTitleAndEmptyDescription() {

        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("Todo Title")
                .build();

        ResponseEntity<?> responseEntity = controller.createTodo(inputDTO);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        TodoDTO todo = (TodoDTO) responseEntity.getBody();

        assertNotNull(todo.getDescription());
        assertNotNull(todo.getId());
        assertEquals(inputDTO.getTitle(), todo.getTitle());
        assertNotNull(todo.getCreatedDate());
        assertEquals(StatusEnum.TODO.getLabel(), todo.getStatus());
    }

    @DisplayName("ÉTANT DONNÉ QUE je fournis un titre et une description valide (maximum 500 caractères), LORSQUE je crée une tâche, ALORS elle est créée avec le titre et la description fournis")
    @Test
    void testCreateTodoWithValidTitleAndValidDescription() {

        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("Todo Title")
                .description("Todo Description")
                .build();

        ResponseEntity<?> responseEntity = controller.createTodo(inputDTO);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        TodoDTO todo = (TodoDTO) responseEntity.getBody();

        assertNotNull(todo);
        assertEquals(inputDTO.getTitle(), todo.getTitle());
        assertEquals(inputDTO.getDescription(), todo.getDescription());
    }

    @DisplayName("ÉTANT DONNÉ QUE je fournis un titre vide ou composé uniquement d'espaces, LORSQUE je tente de créer une tâche, ALORS j'obtiens une erreur \"Title is required\"")
    @Test
    void testCreateTodoWithInvalidTitle() {

        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("     ")
                .build();

        ResponseEntity<?> responseEntity = controller.createTodo(inputDTO);

        assertTrue(responseEntity.getStatusCode().is4xxClientError());

        String body = (String) responseEntity.getBody();

        assertEquals("Title is required", body);
    }

    @DisplayName("ÉTANT DONNÉ QUE je fournis un titre de plus de 100 caractères, LORSQUE je tente de créer une tâche, ALORS j'obtiens une erreur \"Title cannot exceed 100 characters\"")
    @Test
    void testCreateTodoWithTitleTooLong() {
        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("e".repeat(101))
                .build();

        ResponseEntity<?> responseEntity = controller.createTodo(inputDTO);

        assertTrue(responseEntity.getStatusCode().is4xxClientError());

        String body = (String) responseEntity.getBody();

        assertEquals("Title cannot exceed 100 characters", body);
    }

    @DisplayName("ÉTANT DONNÉ QUE je fournis une description de plus de 500 caractères, LORSQUE je tente de créer une tâche, ALORS j'obtiens une erreur \"Description cannot exceed 500 characters\"")
    @Test
    void testCreateTodoWithDescriptionTooLong() {
        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("titleValid")
                .description("e".repeat(501))
                .build();

        ResponseEntity<?> responseEntity = controller.createTodo(inputDTO);

        assertTrue(responseEntity.getStatusCode().is4xxClientError());


        String body = (String) responseEntity.getBody();

        assertEquals("Description cannot exceed 500 characters", body);
    }

    @DisplayName("ÉTANT DONNÉ QUE je fournis une titre qui commence et/ou termine par des espace, LORSQUE je crée une tâche, ALORS elle est créee avec le titre fourni, sans espaces au début ni à la fin\n")
    @Test
    void testCreateTodoWithValidDescription() {
        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("          Todo Title             ")
                .description("validDescription")
                .build();

        ResponseEntity<?> responseEntity = controller.createTodo(inputDTO);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        TodoDTO todo = (TodoDTO) responseEntity.getBody();

        assertNotNull(todo);
        assertEquals("Todo Title", todo.getTitle());
    }

    @DisplayName("ÉTANT DONNÉ QUE j'ai une tâche nouvellement créée, LORSQUE je la consulte, ALORS sa date de création correspond au moment de création à la seconde près")
    @Test
    void testVerifyDateWhenTodoIsCreated() {
        TodoInputDTO inputDTO = TodoInputDTO.builder()
                .title("Todo Title")
                .description("validDescription")
                .build();

        ResponseEntity<?> responseEntity = controller.createTodo(inputDTO);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        TodoDTO todo = (TodoDTO) responseEntity.getBody();

        assertNotNull(todo);
        assertEquals(creationDate, todo.getCreatedDate());
    }

}
