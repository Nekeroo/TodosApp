package com.ynov.todosapp.controllers;

import com.ynov.todosapp.repositories.TodoRepository;
import com.ynov.todosapp.services.TodoService;
import com.ynov.todosapp.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@ActiveProfiles("test")
public abstract class TodoControllerTest {

    @Autowired
    protected TodoRepository todoRepository;

    @Autowired
    protected TodoService service;

    @Autowired
    protected UserService userService;

    protected LocalDate creationDate;

    protected TodosController controller;

    @BeforeEach
    void setUp() {
        creationDate = LocalDate.now();

        controller = new TodosController(service, userService);
    }
}
