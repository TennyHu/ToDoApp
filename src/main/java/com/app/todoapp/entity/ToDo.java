package com.app.todoapp.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ToDo {
    private Long id;
    private boolean completed;
    private String title;
    private Priority priority;
    private LocalDate deadline;
    private Long ownerId;
    private Long teamId;
    private LocalDateTime createTime;

}
