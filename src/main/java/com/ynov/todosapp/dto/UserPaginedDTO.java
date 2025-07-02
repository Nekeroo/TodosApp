package com.ynov.todosapp.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPaginedDTO {

    private Iterable<UserDTO> users;
    private int totalItems;
    private int totalPages;
    private int currentPage;

}
