package com.app.todoapp.services;

import com.app.todoapp.entity.UserTeam;

import java.util.List;


public interface UserTeamService {

    public void joinTeam(Long teamId, Long userId);

    public void leaveTeam(Long teamId, Long userId);

    // 查看该团队所有成员
    public List<UserTeam> getTeamUsers(Long teamId);

    // 查看该成员加入的所有团队
    public List<UserTeam> getUserTeams(Long userId);

    // 查看团队中的具体成员
    public UserTeam getMember(Long teamId, Long userId);

    // 修改成员身份 - 仅限admin操作
    public void modifyUserRole(Long teamId, Long userId, Long targetUserId, String role);
}

