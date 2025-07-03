package com.ynov.todosapp.controllers;

import com.ynov.todosapp.config.jwt.JwtTokenProvider;
import com.ynov.todosapp.dto.UserPaginedDTO;
import com.ynov.todosapp.dto.input.RegisterDTO;
import com.ynov.todosapp.models.Role;
import com.ynov.todosapp.models.User;
import com.ynov.todosapp.repositories.UserRepository;
import com.ynov.todosapp.services.RoleService;
import com.ynov.todosapp.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;




@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@ActiveProfiles("test")
public abstract class AuthenticationControllerTest {

    @Autowired
    protected UserService userService;

    @Autowired
    protected RoleService roleService;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    protected JwtTokenProvider jwtTokenProvider;

    protected RegisterDTO registerDTO;

    protected Role role;

    protected User user;

    protected AuthenticationController controller;

    protected UserPaginedDTO userPaginedDTO;

    @BeforeEach
    void setUp() {
        role = Role.builder().id(1L).label("USER").build();

        user = User.builder()
                .id(1L)
                .name("John Doe")
                .email("johndoe@gmail.com")
                .role(List.of(role))
                .build();

        registerDTO = RegisterDTO.builder().password("toto").name("Dupont Dupont").email("dupont@gmail.com").build();

        controller = new AuthenticationController(authenticationManager, jwtTokenProvider, userService);
    }

}
