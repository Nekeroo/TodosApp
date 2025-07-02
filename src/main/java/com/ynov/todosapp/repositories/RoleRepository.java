package com.ynov.todosapp.repositories;

import com.ynov.todosapp.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role getRoleByLabel(String label);

}
