package com.app.todoapp.entity;

import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
public class UserTeam {
    private Long userId;
    private Long teamId;
    private String role;
}
