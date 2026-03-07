package com.app.todoapp.services;

import com.app.todoapp.entity.User;
import com.app.todoapp.mapper.UserMapper;
import com.app.todoapp.security.JwtUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class UserServiceImpl {
    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;  // 注入接口，不直接 new

    @Autowired
    private JwtUtil jwtUtil;                  // 注入，不 new

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    //@Override
    public void register(String username, String password, String email) {
        // 1. 检查用户名是否已存在
        if (getUserByUsername(username) == null) {
            // 2. 用 BCryptPasswordEncoder 加密密码
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(password);          // 存进数据库的密码必须是加密后的

            // 3. INSERT 进数据库
            User user = new User();
            user.setUsername(username);
            user.setPassword(encodedPassword);
            user.setEmail(email);
            userMapper.register(user);
        } else {
            throw new RuntimeException("用户名已存在已存在");
        }

    }

    public String login(String username, String password) {
        // 1. 根据 username 查用户
        User user = getUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("该用户不存在");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {       // 输入的密码 vs 数据库里的
            throw new RuntimeException("密码错误");
        }

        return jwtUtil.generateToken(user.getId());
    }

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    public User getUserById(Long userId) {
        return userMapper.getUserById(userId);
    }


    public void updateUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userMapper.updateUser(user);
    }


}
