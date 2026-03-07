package com.app.todoapp.services;

import com.app.todoapp.entity.User;

public interface UserService {

    public void register(String username, String password, String email);

    public String login(String username, String password);

    public User getUserByUsername(String username);

    public void updateUser(User user);
}
