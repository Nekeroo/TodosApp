package com.ynov.todosapp.controllers;

import com.ynov.todosapp.dto.TodoDTO;
import com.ynov.todosapp.dto.input.TodoInputDTO;
import com.ynov.todosapp.enums.StatusEnum;
import com.ynov.todosapp.models.Todo;
import com.ynov.todosapp.repositories.TodoRepository;
import com.ynov.todosapp.services.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TodoControllerTest {

    @Autowired
    private TodoService service;

    @MockBean
    private TodoRepository repository;

    private LocalDate creationDate;

    private TodosController controller;
    private List<Todo> savedTodos;

    @BeforeEach
    void setUp() {
        controller = new TodosController(service);
        savedTodos = new ArrayList<>();


        when(repository.save(any(Todo.class))).thenAnswer(invocation -> {
            Todo entity = invocation.getArgument(0, Todo.class);

            entity.setId(savedTodos.size() + 1);
            creationDate = LocalDate.now();
            entity.setCreatedDate(creationDate);

            savedTodos.add(entity);
            return entity;
        });
    }

    @Nested
    @DisplayName("Création d'une TODO")
    class CreateTodo {

        @DisplayName("ÉTANT DONNÉ QUE je fournis un titre valide (non vide, maximum 100 caractères), LORSQUE je crée une tâche, ALORS elle est créée avec un ID unique, le titre fourni, une description vide par défaut, une date de création et le statut \"TODO\"")
        @Test
        void testCreateTodoWithValidTitleAndEmptyDescription() {

            TodoInputDTO inputDTO = TodoInputDTO.builder()
                    .title("validTitle")
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
                    .title("validTitle")
                    .description("validDescription")
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
                    .title("          validTitle             ")
                    .description("validDescription")
                    .build();

            ResponseEntity<?> responseEntity = controller.createTodo(inputDTO);

            assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

            TodoDTO todo = (TodoDTO) responseEntity.getBody();

            assertNotNull(todo);
            assertEquals("validTitle", todo.getTitle());
        }

        @DisplayName("ÉTANT DONNÉ QUE j'ai une tâche nouvellement créée, LORSQUE je la consulte, ALORS sa date de création correspond au moment de création à la seconde près")
        @Test
        void testVerifyDateWhenTodoIsCreated() {
            TodoInputDTO inputDTO = TodoInputDTO.builder()
                    .title("validTitle")
                    .description("validDescription")
                    .build();

            ResponseEntity<?> responseEntity = controller.createTodo(inputDTO);

            assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

            TodoDTO todo = (TodoDTO) responseEntity.getBody();

            assertNotNull(todo);
            assertEquals(creationDate, todo.getCreatedDate());
        }
    }

    @Nested
    @DisplayName("Récupération d'une TODO")
    class GetOneTodo {

        @BeforeEach
        void setUp() {
            creationDate = LocalDate.now();

            savedTodos.add(Todo.builder()
                    .id(1)
                    .title("Todo Title")
                    .description("Todo Description")
                    .createdDate(creationDate)
                    .status(StatusEnum.TODO)
                    .build());

            when(service.getTodoById(anyLong())).thenReturn(Optional.ofNullable(savedTodos.getFirst()));
        }

        @DisplayName("ÉTANT DONNÉ QUE j'ai une tâche existante avec ID valide, LORSQUE je consulte cette tâche, ALORS j'obtiens tous ses détails : ID, titre, description, statut, date de création, etc..")
        @Test
        void testGetOneTodo() {

            ResponseEntity<?> repsonse = controller.retrieveTodoById("1");

            assertTrue(repsonse.getStatusCode().is2xxSuccessful());

            TodoDTO todoDTO = (TodoDTO) repsonse.getBody();
            assertNotNull(todoDTO);
            assertNotNull(todoDTO.getId());
            assertNotNull(todoDTO.getTitle());
            assertNotNull(todoDTO.getDescription());
            assertNotNull(todoDTO.getCreatedDate());
            assertNotNull(todoDTO.getStatus());

        }

        @DisplayName("ÉTANT DONNÉ QUE je consulte une tâche avec un ID inexistant, LORSQUE je fais la demande, ALORS j'obtiens une erreur \"Task not found\" avec, si web, le code 404\n")
        @Test
        void testGetOneTodoNotFound() {

            when(service.getTodoById(1L)).thenReturn(Optional.empty());

            ResponseEntity<?> repsonse = controller.retrieveTodoById("1");

            assertTrue(repsonse.getStatusCode().is4xxClientError());
            String body = (String) repsonse.getBody();

            assertEquals("Todo not found", body);
        }

        @DisplayName("ÉTANT DONNÉ QUE je consulte une tâche avec un ID au mauvais format, LORSQUE je fais la demande, ALORS j'obtiens une erreur \"Invalid ID format\"\n")
        @Test
        void testGetOneTodoWithIdWithIncorrectFormat() {
            ResponseEntity<?> repsonse = controller.retrieveTodoById("azerty");

            assertTrue(repsonse.getStatusCode().is4xxClientError());

            String body = (String) repsonse.getBody();

            assertEquals("Invalid ID format", body);
        }

    }

    @Nested
    @DisplayName("Modification d'une TODO")
    class UpdateOneTodo {
        @BeforeEach
        void setUp() {
            creationDate = LocalDate.now();

            savedTodos.add(Todo.builder()
                    .id(1)
                    .title("Todo Title")
                    .description("Todo Description")
                    .createdDate(creationDate)
                    .status(StatusEnum.TODO)
                    .build());

            when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(savedTodos.getFirst()));
        }

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
    }

    @Nested
    @DisplayName("Update status one TODO")
    class UpdateStatusOneTodo {


    }

}