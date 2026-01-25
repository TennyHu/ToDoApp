package com.app.todoapp.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private boolean completed;
    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.LOW;       // by default, low priority level



}
