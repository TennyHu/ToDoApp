package com.app.todoapp.entity;

import lombok.Data;

@Data
public class UserTeam {
    private int userId;
    private int teamId;
    private String role;
}
