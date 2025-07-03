package com.ynov.todosapp.controllers.authentication;


import com.ynov.todosapp.controllers.AuthenticationControllerTest;
import com.ynov.todosapp.dto.UserDTO;
import com.ynov.todosapp.dto.input.RegisterDTO;
import com.ynov.todosapp.exceptions.user.EmailAlreadyTaken;
import com.ynov.todosapp.exceptions.user.InvalidEmail;
import com.ynov.todosapp.exceptions.user.NameIsRequired;
import com.ynov.todosapp.exceptions.user.NameIsTooLong;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest extends AuthenticationControllerTest {

    @DisplayName("ÉTANT DONNÉ QUE je fournis un nom valide (non vide, maximum 50 caractères) et un email valide, LORSQUE je crée un utilisateur, ALORS il est créé avec un ID unique, le nom et l'email fournis ainsi que sa date de création\n")
    @Test
    void testRegisterOneUserWithValidInformations() {
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
        RegisterDTO registerDTO = RegisterDTO.builder()
                .name("testUser")
                .email("mathieugrattard1@gmail.com")
                .password("validPassword123")
                .build();

        assertThrows(EmailAlreadyTaken.class, () -> controller.registerUser(registerDTO));
    }

    @DisplayName("ÉTANT DONNÉ QUE je fournis un email au format invalide, LORSQUE je tente de créer l'utilisateur, ALORS j'obtiens une erreur \"Invalid email format\"\n")
    @Test
    void testRegisterOneUserWithInvalidEmailFormat() {
        RegisterDTO registerDTOInvalidEmailFormat = RegisterDTO.builder()
                .name("testUser")
                .email("mgmail.com")
                .password("validPassword123")
                .build();

        assertThrows(InvalidEmail.class, () -> controller.registerUser(registerDTOInvalidEmailFormat));
    }

    @DisplayName("ÉTANT DONNÉ QUE je fournis un nom vide ou composé uniquement d'espaces, LORSQUE je tente de créer l'utilisateur, ALORS j'obtiens une erreur \"Name is required\"\n")
    @Test
    void testRegisterOneUserWithInvalidName() {
        RegisterDTO registerDTO1WithInvalidName = RegisterDTO.builder()
                .name("")
                .email("H9F0A@example.com")
                .password("validPassword123")
                .build();


        assertThrows(NameIsRequired.class, () -> controller.registerUser(registerDTO1WithInvalidName));

        RegisterDTO registerDTO2WithInvalidName = RegisterDTO.builder()
                .name("   ")
                .email("H9F0A@example.com")
                .password("validPassword123")
                .build();

        assertThrows(NameIsRequired.class, () -> controller.registerUser(registerDTO2WithInvalidName));
    }

    @DisplayName("ÉTANT DONNÉ QUE je fournis un nom de plus de 50 caractères, LORSQUE je tente de créer l'utilisateur, ALORS j'obtiens une erreur \"Name cannot exceed 50 characters\"\n")
    @Test
    void testRegisterOneUserWithNameTooLong() {
        RegisterDTO registerDTO01WithNameTooLong = RegisterDTO.builder()
                .name("e".repeat(51))
                .email("H9F0A@example.com")
                .password("validPassword123")
                .build();

        assertThrows(NameIsTooLong.class, () -> controller.registerUser(registerDTO01WithNameTooLong));
    }

}
