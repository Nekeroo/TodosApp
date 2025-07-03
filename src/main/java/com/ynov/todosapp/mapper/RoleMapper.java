package com.ynov.todosapp.mapper;

import com.ynov.todosapp.dto.RoleDTO;
import com.ynov.todosapp.models.Role;

import java.util.List;
import java.util.stream.Collectors;

public class RoleMapper {

    public static RoleDTO toDto(Role role) {
        return RoleDTO.builder()
                .label(role.getLabel())
                .build();
    }

    public static List<RoleDTO> toDto(List<Role> roles) {
        return roles.stream().map(RoleMapper::toDto).collect(Collectors.toList());
    }

    public static List<String> toListString(List<Role> roles) {
        return roles.stream().map(Role::getLabel).collect(Collectors.toList());
    }

}
