package com.ynov.todosapp.repositories;

import com.ynov.todosapp.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long>,CrudRepository<User, Long> {

    Optional<User> getUserByEmail(String email);

    List<User> email(String email);


}
