package com.app.todoapp.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Team {
    private Long id;
    private String name;
    private LocalDateTime createTime;
    private Long createdBy;
}
