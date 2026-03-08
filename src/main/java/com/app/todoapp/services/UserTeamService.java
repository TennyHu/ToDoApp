package com.app.todoapp.services;

import com.app.todoapp.entity.UserTeam;

import java.util.List;


public interface UserTeamService {

    // 权限 - 当前用户
    // 操作 - 加入团队
    public void joinTeam(Long teamId, Long userId);

    // 权限 - 当前用户
    // 操作 - 退出团队（若当前用户为唯一admin，需要接管）
    public void leaveTeam(Long teamId, Long userId);

    // 权限 - 无
    // 操作 - 查看当前团队的所有用户
    public List<UserTeam> getTeamUsers(Long teamId);

    // 权限 - 无
    // 操作 - 查看当前用户加入的所有团队
    public List<UserTeam> getUserTeams(Long userId);

    // 权限 - 无
    // 操作 - 查看团队中的具体成员（仅限内部使用）
    public UserTeam getMember(Long teamId, Long userId);

    // 权限 - admin
    // 操作 - 修改成员身份
    public void modifyUserRole(Long teamId, Long userId, Long targetUserId, String role);
}

