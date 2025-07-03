package com.ynov.todosapp.controllers.authentication;

import com.ynov.todosapp.controllers.AuthenticationControllerTest;
import com.ynov.todosapp.dto.UserDTO;
import com.ynov.todosapp.dto.input.LoginDTO;
import com.ynov.todosapp.exceptions.user.WrongCredentials;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends AuthenticationControllerTest {

    @DisplayName("ETANT DONNE que je possède une email valide et un mot de passe correct, LORSQUE je tente de me connecter, ALORS je me connecte avec un token JWT\n")
    @Test
    void testLoginWithValidInformations() {
        LoginDTO loginDTO = LoginDTO.builder()
                .email("toto@gmail.com")
                .password("toto")
                .build();

        ResponseEntity<?> response = controller.loginUser(loginDTO);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        UserDTO userDTO = (UserDTO) response.getBody();

        assertAll(
                () -> assertNotNull(userDTO),
                () -> assertNotNull(userDTO.getTokenJwt()),
                () -> assertEquals(loginDTO.getEmail(), userDTO.getEmail())
        );
    }

    @DisplayName("ETANT DONNE que je possède une email et un mot de passe incorrect, LORSQUE je tente de me connecter, ALORS je reçois une erreur 'Wrong Credentials' et je ne me connecte pas\n")
    @Test
    void testLoginWithInvalidInformations() {
        LoginDTO loginDTO = LoginDTO.builder()
                .email("inconnu@gmail.com")
                .password("invalidPassword")
                .build();

        assertThrows(WrongCredentials.class, () -> controller.loginUser(loginDTO));

        LoginDTO loginDTO2 = LoginDTO.builder()
                .email("toto@gmail.com")
                .password("invalidPassword")
                .build();

        assertThrows(WrongCredentials.class, () -> controller.loginUser(loginDTO2));
    }
}
