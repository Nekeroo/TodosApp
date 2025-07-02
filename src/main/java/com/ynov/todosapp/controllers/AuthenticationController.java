package com.ynov.todosapp.controllers;

import com.ynov.todosapp.config.jwt.JwtTokenProvider;
import com.ynov.todosapp.dto.TodosPaginedDTO;
import com.ynov.todosapp.dto.UserDTO;
import com.ynov.todosapp.dto.UserPaginedDTO;
import com.ynov.todosapp.dto.input.LoginDTO;
import com.ynov.todosapp.dto.input.RegisterDTO;
import com.ynov.todosapp.exceptions.user.EmailAlreadyTaken;
import com.ynov.todosapp.exceptions.user.WrongCredentiels;
import com.ynov.todosapp.mapper.RoleMapper;
import com.ynov.todosapp.mapper.TodoMapper;
import com.ynov.todosapp.mapper.UserMapper;
import com.ynov.todosapp.models.Role;
import com.ynov.todosapp.models.User;
import com.ynov.todosapp.services.UserService;
import com.ynov.todosapp.utils.UserValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.memory.UserAttribute;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth/")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Value("${jwt.secret}")
    private String jwtSecret;

    @GetMapping("/")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDto) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String userName = loginDto.getEmail();
            User user = userService.getUserByEmail(loginDto.getEmail());

            String token = jwtTokenProvider.generateToken(userName, RoleMapper.toListString(user.getRole()));

            UserDTO userDTO = UserMapper.toDTO(user, token);
            return ResponseEntity.ok().body(userDTO);
        } catch (AuthenticationException e) {
            throw new WrongCredentiels();
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO) {

        if (this.userService.getUserByEmail(registerDTO.getEmail()) != null) {
            throw new EmailAlreadyTaken();
        }

        UserValidator.validateUsername(registerDTO.getUsername());
        UserValidator.validateEmail(registerDTO.getEmail());

        User user = userService.registerUser(registerDTO);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(registerDTO.getEmail(), registerDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole().stream().map(Role::getLabel).collect(Collectors.toList()));

        UserDTO userDTO = UserMapper.toDTO(user, token);
        return ResponseEntity.ok().body(userDTO);

    }

    @GetMapping("/users/retrieve")
    public ResponseEntity<?> retrieveAllUsers(@RequestParam(defaultValue = "0") int page) {
        UserPaginedDTO users = UserMapper.userPageToDTO(userService.getAllUsers(page));
        return ResponseEntity.ok().body(users);
    }

}
