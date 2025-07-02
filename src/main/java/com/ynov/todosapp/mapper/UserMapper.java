package com.ynov.todosapp.mapper;


import com.ynov.todosapp.dto.UserDTO;
import com.ynov.todosapp.models.User;

public class UserMapper {

    public static UserDTO toDTO(final User user, final String token) {
        return UserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .tokenJwt(token)
                .roles(RoleMapper.toDto(user.getRole()))
                .build();
    }

}
