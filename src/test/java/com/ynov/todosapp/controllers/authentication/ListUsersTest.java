package com.ynov.todosapp.controllers.authentication;

import com.ynov.todosapp.controllers.AuthenticationControllerTest;
import com.ynov.todosapp.dto.UserDTO;
import com.ynov.todosapp.dto.UserPaginedDTO;
import com.ynov.todosapp.models.Role;
import com.ynov.todosapp.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class ListUsersTest extends AuthenticationControllerTest {

    @DisplayName("ÉTANT DONNÉ QUE j'ai plusieurs utilisateurs créés, LORSQUE je demande la liste des utilisateurs, ALORS j'obtiens tous les utilisateurs avec leurs informations (ID, nom, email)\n")
    @Test
    void testRetrieveUsersPaginated() {
        when(userService.getAllUsers(anyInt())).thenReturn(new PageImpl<>(users, PageRequest.of(anyInt(), 10), users.size()));

        ResponseEntity<?> response = controller.retrieveAllUsers(0);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        UserPaginedDTO responseBody = (UserPaginedDTO) response.getBody();

        List<UserDTO> usersRetrieves = new ArrayList<>();

        for (UserDTO user: responseBody.getUsers()) {
            usersRetrieves.add(user);
        }

        assertAll(
                () -> assertNotNull(responseBody),
                () -> assertEquals(users.size(), usersRetrieves.size()
                ));
    }

    @DisplayName("ÉTANT DONNÉ QUE je n'ai aucun utilisateur, LORSQUE je demande la liste, ALORS j'obtiens une liste vide\n")
    @Test
    void testRestrieveUsersPaginatedWhenNoUsers() {
        when(userService.getAllUsers(anyInt())).thenReturn(new PageImpl<>(new ArrayList<>(), PageRequest.of(anyInt(), 10), 0));

        ResponseEntity<?> response = controller.retrieveAllUsers(0);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        UserPaginedDTO responseBody = (UserPaginedDTO) response.getBody();

        List<UserDTO> usersRetrieves = new ArrayList<>();

        for (UserDTO user: responseBody.getUsers()) {
            usersRetrieves.add(user);
        }

        assertAll(
                () -> assertNotNull(responseBody),
                () -> assertTrue(usersRetrieves.isEmpty())
        );
    }

    @DisplayName("ÉTANT DONNÉ QUE je demande la liste des utilisateurs, LORSQUE j'exécute la requête, ALORS les utilisateurs sont triés par nom par défaut\n")
    @Test
    void testRetrieveUserFilteredByName() {
        User userMocked = User.builder().id(2L).name("Dupont").role(List.of(role)).build();

        users.add(userMocked);

        when(userService.getAllUsers(anyInt())).thenReturn(new PageImpl<>(users, PageRequest.of(anyInt(), 10), users.size()));

        ResponseEntity<?> response = controller.retrieveAllUsers(0);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        UserPaginedDTO responseBody = (UserPaginedDTO) response.getBody();

        List<UserDTO> usersRetrieves = new ArrayList<>();

        for (UserDTO user: responseBody.getUsers()) {
            usersRetrieves.add(user);
        }

        assertAll(
                () -> assertNotNull(responseBody),
                () -> assertEquals(users.size(), usersRetrieves.size()),
                () -> {
                    assertEquals(userMocked.getName(), usersRetrieves.getFirst().getName());
                },
                () -> assertTrue(usersRetrieves.stream().allMatch(user -> user.getName() != null))
        );
    }

}
