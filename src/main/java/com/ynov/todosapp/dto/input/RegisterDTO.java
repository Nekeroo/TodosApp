package com.ynov.todosapp.dto.input;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterDTO {

    private String username;
    private String email;
    private String password;

}
