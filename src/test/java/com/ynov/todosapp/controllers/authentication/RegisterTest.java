package com.ynov.todosapp.controllers.authentication;


import com.ynov.todosapp.controllers.AuthenticationControllerTest;
import com.ynov.todosapp.dto.UserDTO;
import com.ynov.todosapp.dto.input.RegisterDTO;
import com.ynov.todosapp.exceptions.user.EmailAlreadyTaken;
import com.ynov.todosapp.exceptions.user.InvalidEmail;
import com.ynov.todosapp.exceptions.user.NameIsRequired;
import com.ynov.todosapp.exceptions.user.NameIsTooLong;
import com.ynov.todosapp.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegisterTest extends AuthenticationControllerTest {

    @DisplayName("ÉTANT DONNÉ QUE je fournis un nom valide (non vide, maximum 50 caractères) et un email valide, LORSQUE je crée un utilisateur, ALORS il est créé avec un ID unique, le nom et l'email fournis ainsi que sa date de création\n")
    @Test
    void testRegisterOneUserWithValidInformations() {
        when(userService.getUserByEmail(registerDTO.getEmail())).thenReturn(null);

        User mockUser = User.builder()
                .id(1L)
                .name(registerDTO.getUsername())
                .email(registerDTO.getEmail())
                .role(List.of(role))
                .build();

        when(userService.registerUser(any(RegisterDTO.class))).thenReturn(mockUser);
        when(jwtTokenProvider.generateToken(any(), anyList())).thenReturn("mockToken");
        when(authenticationManager.authenticate(any())).thenReturn(mock(Authentication.class));

        ResponseEntity<?> response = controller.registerUser(registerDTO);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        UserDTO userDTO = (UserDTO) response.getBody();
        assertAll(() -> {
            assertNotNull(userDTO);
            assertNotNull(userDTO.getTokenJwt());
        });
    }

    @DisplayName("ÉTANT DONNÉ QUE je fournis un email déjà utilisé par un autre utilisateur, LORSQUE je tente de créer l'utilisateur, ALORS j'obtiens une erreur \"Email already in use\"\n")
    @Test
    void testRegisterOneUserWithEmailAlreadyTaken() {
        when(userService.getUserByEmail(registerDTO.getEmail())).thenReturn(user);

        assertThrows(EmailAlreadyTaken.class, () -> controller.registerUser(registerDTO));
    }

    @DisplayName("ÉTANT DONNÉ QUE je fournis un email au format invalide, LORSQUE je tente de créer l'utilisateur, ALORS j'obtiens une erreur \"Invalid email format\"\n")
    @Test
    void testRegisterOneUserWithInvalidEmailFormat() {
        when(userService.getUserByEmail(anyString())).thenReturn(null);

        RegisterDTO registerDTOInvalidEmailFormat = RegisterDTO.builder()
                .username("testUser")
                .email("invalid-email-format")
                .password("validPassword123")
                .build();

        assertThrows(InvalidEmail.class, () -> controller.registerUser(registerDTOInvalidEmailFormat));
    }

    @DisplayName("ÉTANT DONNÉ QUE je fournis un nom vide ou composé uniquement d'espaces, LORSQUE je tente de créer l'utilisateur, ALORS j'obtiens une erreur \"Name is required\"\n")
    @Test
    void testRegisterOneUserWithInvalidName() {
        when(userService.getUserByEmail(anyString())).thenReturn(null);

        RegisterDTO registerDTO1WithInvalidName = RegisterDTO.builder()
                .username("")
                .email("H9F0A@example.com")
                .password("validPassword123")
                .build();


        assertThrows(NameIsRequired.class, () -> controller.registerUser(registerDTO1WithInvalidName));

        RegisterDTO registerDTO2WithInvalidName = RegisterDTO.builder()
                .username("   ")
                .email("H9F0A@example.com")
                .password("validPassword123")
                .build();

        assertThrows(NameIsRequired.class, () -> controller.registerUser(registerDTO2WithInvalidName));
    }

    @DisplayName("ÉTANT DONNÉ QUE je fournis un nom de plus de 50 caractères, LORSQUE je tente de créer l'utilisateur, ALORS j'obtiens une erreur \"Name cannot exceed 50 characters\"\n")
    @Test
    void testRegisterOneUserWithNameTooLong() {
        when(userService.getUserByEmail(anyString())).thenReturn(null);

        RegisterDTO registerDTO01WithNameTooLong = RegisterDTO.builder()
                .username("e".repeat(51))
                .email("H9F0A@example.com")
                .password("validPassword123")
                .build();

        assertThrows(NameIsTooLong.class, () -> controller.registerUser(registerDTO01WithNameTooLong));
    }

}
