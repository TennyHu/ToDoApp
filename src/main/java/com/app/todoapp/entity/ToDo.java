package com.app.todoapp.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ToDo {
    private int id;
    private boolean completed;
    private String title;
    private Priority priority;
    private LocalDate deadline;
    private int ownerId;
    private int teamId;
    private LocalDateTime createTime;

}
