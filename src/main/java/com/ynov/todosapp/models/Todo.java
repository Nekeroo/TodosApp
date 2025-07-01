package com.ynov.todosapp.models;

import com.ynov.todosapp.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Optional;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    /*
    Obligatoire
    Entre 1 et 100 caractères
     */
    private String title;

    /*
    Optionel
    500 car max
     */
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

    @Column(name = "created_date")
    private LocalDate createdDate;

}
