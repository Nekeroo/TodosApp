package com.ynov.todosapp.mapper;


import com.ynov.todosapp.dto.TodoDTO;
import com.ynov.todosapp.dto.TodosPaginedDTO;
import com.ynov.todosapp.dto.UserDTO;
import com.ynov.todosapp.dto.UserPaginedDTO;
import com.ynov.todosapp.models.Todo;
import com.ynov.todosapp.models.User;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserDTO toDTO(final User user, final String token) {
        return UserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .tokenJwt(token)
                .roles(RoleMapper.toDto(user.getRole()))
                .build();
    }

    public static UserDTO toDTO(final User user) {
        return UserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .roles(RoleMapper.toDto(user.getRole()))
                .build();
    }

    public static UserPaginedDTO userPageToDTO(Page<User> userPage) {
        List<UserDTO> users = new ArrayList<>(userPage.getContent().stream()
                .map(UserMapper::toDTO)
                .toList());

        return UserPaginedDTO.builder()
                .currentPage(userPage.getNumber())
                .totalItems((int) userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .users(users)
                .build();
    }

}
