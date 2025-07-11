package com.ynov.todosapp.services;

import com.ynov.todosapp.dto.input.RegisterDTO;
import com.ynov.todosapp.exceptions.user.UserNotFound;
import com.ynov.todosapp.models.Role;
import com.ynov.todosapp.models.User;
import com.ynov.todosapp.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserService(RoleService roleService, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email)
                .orElseThrow(UserNotFound::new);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFound::new);
    }

    public boolean isUserExist(String email) {
        return userRepository.getUserByEmail(email).isPresent();
    }

    public User registerUser(RegisterDTO registerDTO) {
        Role role = roleService.getRoleByLabel("USER");

        User user = User.builder()
                .role(List.of())
                .name(registerDTO.getName())
                .email(registerDTO.getEmail())
                .role(List.of(role))
                .password(bCryptPasswordEncoder.encode(registerDTO.getPassword()))
                .build();

        return userRepository.save(user);
    }

    public Page<User> getAllUsers(int page) {
        return userRepository.findAll(PageRequest.of(page, 10, Sort.by("name").ascending()));
    }

    @Transactional
    public void removeRoleFromUser(long userId, long roleId) {
        User user  = userRepository.findById(userId).orElseThrow();
        Role role  = roleService.getRoleById(roleId);

        user.getRole().remove(role);

        userRepository.save(user);
    }

}
