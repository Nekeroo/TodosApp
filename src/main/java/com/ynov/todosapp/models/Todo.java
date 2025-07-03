package com.ynov.todosapp.models;

import com.ynov.todosapp.enums.PriorityEnum;
import com.ynov.todosapp.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private PriorityEnum priority;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User userAffected;

}
