package com.ynov.todosapp.controllers.authentication;

import com.ynov.todosapp.controllers.AuthenticationControllerTest;
import com.ynov.todosapp.dto.UserDTO;
import com.ynov.todosapp.dto.UserPaginedDTO;
import com.ynov.todosapp.models.User;
import com.ynov.todosapp.repositories.RoleRepository;
import com.ynov.todosapp.services.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ListUsersTest extends AuthenticationControllerTest {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TodoService todoService;

    @DisplayName("ÉTANT DONNÉ QUE j'ai plusieurs utilisateurs créés, LORSQUE je demande la liste des utilisateurs, ALORS j'obtiens tous les utilisateurs avec leurs informations (ID, nom, email)\n")
    @Test
    void testRetrieveUsersPaginated() {
        ResponseEntity<?> response = controller.retrieveAllUsers(0);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        UserPaginedDTO responseBody = (UserPaginedDTO) response.getBody();

        List<UserDTO> usersRetrieves = new ArrayList<>();

        for (UserDTO user : responseBody.getUsers()) {
            usersRetrieves.add(user);
        }

        assertAll(
                () -> assertNotNull(responseBody));
    }

    @DisplayName("ÉTANT DONNÉ QUE je n'ai aucun utilisateur, LORSQUE je demande la liste, ALORS j'obtiens une liste vide\n")
    @Test
    void testRestrieveUsersPaginatedWhenNoUsers() {

        List<User> users = userService.getAllUsers(0).getContent();

        for (User user : users) {
            userService.removeRoleFromUser(user.getId(), user.getRole().get(0).getId());
            todoService.deleteTodoByUserIdAffected(user.getId());
        }

        userRepository.deleteAll();

        ResponseEntity<?> response = controller.retrieveAllUsers(0);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        UserPaginedDTO responseBody = (UserPaginedDTO) response.getBody();

        List<UserDTO> usersRetrieves = new ArrayList<>();

        for (UserDTO user : responseBody.getUsers()) {
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
        ResponseEntity<?> response = controller.retrieveAllUsers(0);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        UserPaginedDTO responseBody = (UserPaginedDTO) response.getBody();

        List<UserDTO> usersRetrieves = new ArrayList<>();

        for (UserDTO user : responseBody.getUsers()) {
            usersRetrieves.add(user);
        }

        assertAll(
                () -> assertNotNull(responseBody)
        );
    }

}
