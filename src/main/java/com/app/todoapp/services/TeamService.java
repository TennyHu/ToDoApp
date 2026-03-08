package com.app.todoapp.services;

import com.app.todoapp.entity.Team;
import com.app.todoapp.entity.ToDo;
import com.app.todoapp.entity.UserTeam;

public interface TeamService {

    // 权限 - 无
    // 操作 - 创建团队的用户为admin，@Transactional
    public Team createTeam(String teamName, Long userId);

    // 权限 - 无
    // 操作 - 通过team id查看team
    public Team getTeamById(Long id);

    // 权限 - admin
    // 操作 - 更新团队信息
    public void updateTeam(Long teamId, Long userId, Team team);

    // 权限 - admin
    // 操作 - 解散团队
    public void deleteTeam(Long teamId, Long userId, Team team);
}
