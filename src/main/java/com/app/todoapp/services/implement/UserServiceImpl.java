package com.app.todoapp.services.implement;

import com.app.todoapp.entity.User;
import com.app.todoapp.mapper.UserMapper;
import com.app.todoapp.security.JwtUtil;
import com.app.todoapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void register(String username, String password, String email) {

        String encodedPassword = passwordEncoder.encode(password);          // 存进数据库的密码必须是加密后

        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setEmail(email);
        try {
            userMapper.register(user);
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("用户名已存在");
        }

    }

    @Override
    public String login(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("该用户不存在");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {       // 输入的密码 vs 数据库里的
            throw new RuntimeException("密码错误");
        }

        return jwtUtil.generateToken(user.getId());
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userMapper.getUserByUsername(username);
        if (username == null || user == null) {
            throw new RuntimeException("该用户不存在");
        }
        return user;
    }

    @Override
    public User getUserById(Long userId) {
        User user = userMapper.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在");
        }
        return user;
    }

    @Override
    public void updateUser(User user, Long userId) {
        if (!user.getId().equals(userId)) {
            throw new RuntimeException("该用户无权修改用户信息");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userMapper.updateUser(user);
    }


}
