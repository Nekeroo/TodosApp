package com.ynov.todosapp.services;

import com.ynov.todosapp.exceptions.TaskNotFound;
import com.ynov.todosapp.models.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class TodoServiceTest {

    @Autowired
    private TodoService todoService;

    @DisplayName("ETANT DONNE que j'ai une todo en base de donnée, LORSQUE je cherche une todo par son id, ALORS je la récupère avec ses informations")
    @Test
    void testGetTodoByValidID() {
        Todo todo = todoService.getTodoById(1L);
        assertAll(() -> assertNotNull(todo));
    }

    @DisplayName("ETANT DONNE que j'ai une todo en base de donnée, LORSQUE je cherche une todo par son id, ALORS je la récupère avec ses informations")
    @Test
    void testGetTodoByInvalidID() {
        assertThrows(TaskNotFound.class, () -> todoService.getTodoById(2L));
    }
}
