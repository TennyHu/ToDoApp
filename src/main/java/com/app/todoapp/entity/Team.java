package com.app.todoapp.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Team {
    private int id;
    private String name;
    private LocalDateTime createTime;
    private int createdBy;
}
