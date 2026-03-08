package com.app.todoapp.controller;


import com.app.todoapp.entity.Result;
import com.app.todoapp.entity.User;
import com.app.todoapp.services.implement.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        userServiceImpl.register(user.getUsername(), user.getPassword(), user.getEmail());
        return Result.success("注册成功！");
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        String token = userServiceImpl.login(user.getUsername(), user.getPassword());
        return Result.success(token);
    }

    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        User user = userServiceImpl.getUserByUsername(username);
        return user;
    }

    @GetMapping("/profile")
    public User getProfile(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return userServiceImpl.getUserById(userId);
    }

    @PutMapping("/{id}")
    public Result updateUser(@RequestBody User user,
                             HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        userServiceImpl.updateUser(user, userId);
        return Result.success();
    }
}
