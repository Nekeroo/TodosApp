package com.ynov.todosapp.dto.input;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginDTO {

    private String email;

    private String password;

}
