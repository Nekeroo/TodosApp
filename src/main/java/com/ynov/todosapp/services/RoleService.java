package com.ynov.todosapp.services;

import com.ynov.todosapp.models.Role;
import com.ynov.todosapp.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByLabel(String label) {
        return roleRepository.getRoleByLabel(label);
    }
}
