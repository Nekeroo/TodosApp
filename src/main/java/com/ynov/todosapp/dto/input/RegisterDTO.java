package com.ynov.todosapp.dto.input;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterDTO {

    private String name;
    private String email;
    private String password;

}
