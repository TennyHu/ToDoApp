package com.app.todoapp.services;

import com.app.todoapp.entity.Team;
import com.app.todoapp.entity.ToDo;

public interface TeamService {

    // 创建者自动为admin
    public Team createTeam(String teamName, Long userId);

    public Team getTeamById(Long id);

    // 只有admin可以进行操作
    public void updateTeam(Team team);

    // 只有admin可以进行操作
    public void deleteTeam(Team team);
}
