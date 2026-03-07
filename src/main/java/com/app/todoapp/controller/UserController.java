package com.app.todoapp.controller;


import com.app.todoapp.entity.Result;
import com.app.todoapp.entity.User;
import com.app.todoapp.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")     // 允许前端为不同端口
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

    @PutMapping("/login")
    public Result login(@RequestBody User user) {
        String token = userServiceImpl.login(user.getUsername(), user.getPassword());
        return Result.success(token);
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        User user = userServiceImpl.getUserByUsername(username);
        return user;
    }

    @PutMapping("/{id}")
    public Result updateUser(@RequestBody User user) {
        userServiceImpl.updateUser(user);
        return Result.success();
    }
}
