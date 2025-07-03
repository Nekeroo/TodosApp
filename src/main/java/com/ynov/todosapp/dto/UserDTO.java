package com.ynov.todosapp.dto;

import lombok.*;
import org.hibernate.annotations.SecondaryRow;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String name;

    private String email;

    private String tokenJwt;

    private List<RoleDTO> roles;
}
