package com.app.todoapp.services;

import com.app.todoapp.entity.User;

public interface UserService {

    // 权限 - 无
    // 操作 - 注册新用户
    public void register(String username, String password, String email);

    // 权限 - 无
    // 操作 - 登录
    public String login(String username, String password);

    // 权限 - 无
    // 操作 - 通过用户名查找用户（仅内部使用）
    public User getUserByUsername(String username);

    // 权限 - 无
    // 操作 - 通过userId查找用户
    public User getUserById(Long userId);

    // 权限 - 当前用户
    // 操作 - 更新用户信息
    public void updateUser(User user, Long userId);
}
