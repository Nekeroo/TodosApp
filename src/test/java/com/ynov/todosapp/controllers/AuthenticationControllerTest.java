package com.ynov.todosapp.controllers;

import com.ynov.todosapp.config.jwt.JwtTokenProvider;
import com.ynov.todosapp.dto.UserPaginedDTO;
import com.ynov.todosapp.dto.input.RegisterDTO;
import com.ynov.todosapp.models.Role;
import com.ynov.todosapp.models.User;
import com.ynov.todosapp.repositories.RoleRepository;
import com.ynov.todosapp.repositories.UserRepository;
import com.ynov.todosapp.services.RoleService;
import com.ynov.todosapp.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public abstract class AuthenticationControllerTest {

    @Mock
    protected UserRepository userRepository;

    @Mock
    protected UserService userService;

    @Mock
    protected RoleRepository roleRepository;

    @Mock
    protected RoleService roleService;

    @Mock
    protected AuthenticationManager authenticationManager;

    @Mock
    protected JwtTokenProvider jwtTokenProvider;

    protected List<User> users;

    protected RegisterDTO registerDTO;

    protected Role role;

    protected User user;

    protected AuthenticationController controller;

    protected UserPaginedDTO userPaginedDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        users = new ArrayList<>();

        role = Role.builder().id(1L).label("USER").build();

        user = User.builder()
                .id(1L)
                .name("John Doe")
                .email("johndoe@gmail.com")
                .role(List.of(role))
                .build();

        registerDTO = RegisterDTO.builder().password("toto").username("Dupont Dupont").email("dupont@gmail.com").build();

        controller = new AuthenticationController(authenticationManager, jwtTokenProvider, userService);
    }

}
