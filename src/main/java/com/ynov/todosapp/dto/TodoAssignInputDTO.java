package com.ynov.todosapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TodoAssignInputDTO {

    private Long idUser;

    private boolean shouldAssign;

}
