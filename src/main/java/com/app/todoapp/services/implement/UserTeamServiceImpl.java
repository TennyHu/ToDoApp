package com.app.todoapp.services.implement;

import com.app.todoapp.entity.User;
import com.app.todoapp.entity.UserTeam;
import com.app.todoapp.mapper.UserTeamMapper;
import com.app.todoapp.services.UserTeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserTeamServiceImpl implements UserTeamService {

    private final UserTeamMapper userTeamMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    public UserTeamServiceImpl(UserTeamMapper userTeamMapper) {
        this.userTeamMapper = userTeamMapper;
    }

    @Override
    public void joinTeam(Long teamId, Long userId) {
        UserTeam userTeam = userTeamMapper.getMember(teamId, userId);
        if (userTeam != null) {
            throw new RuntimeException("该成员已存在于team中");
        }

        UserTeam userTeam1 = new UserTeam();
        userTeam1.setTeamId(teamId);
        userTeam1.setUserId(userId);
        userTeam1.setRole("member");
        userTeamMapper.joinTeam(userTeam1);

        redisTemplate.delete("team:" + teamId +":members");
        redisTemplate.delete("user:" + userId + ":teams");
    }

    @Override
    public void leaveTeam(Long teamId, Long userId) {
        UserTeam userTeam = userTeamMapper.getMember(teamId, userId);
        if (userTeam == null) {
            throw new RuntimeException("该成员已不存在team中");
        }

        // admin 不能直接退出
        if ("admin".equals(userTeam.getRole())) {
            if (userTeamMapper.countAdmins(teamId) <= 1) {
                throw new RuntimeException("admin 不能直接退出，请先转让管理员权限");
            }
        }

        userTeamMapper.leaveTeam(teamId, userId);
        redisTemplate.delete("team:" + teamId +":members");
        redisTemplate.delete("user:" + userId + ":teams");
    }


    @Override
    public List<UserTeam> getTeamUsers(Long teamId) {

        Object cachedTeamUsers = redisTemplate.opsForValue().get("team:" + teamId +":members");
        if (cachedTeamUsers != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            List<UserTeam> list = objectMapper.convertValue(
                    cachedTeamUsers,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, UserTeam.class)
            );
            if (list.isEmpty()) {
                throw new RuntimeException("当前team不存在");
            }
            System.out.println("Cache hit: teamId=" + teamId);
            return list;
        }

        List<UserTeam> list = userTeamMapper.getTeamUsers(teamId);
        if (list.isEmpty()) {
            throw new RuntimeException("当前team不存在");
        }
        System.out.println("Cache miss: teamId=" + teamId);
        redisTemplate.opsForValue().set("team:" + teamId +":members", list, 5, TimeUnit.MINUTES);
        return list;
    }


    @Override
    public List<UserTeam> getUserTeams(Long userId) {
        Object cachedUserTeams = redisTemplate.opsForValue().get("user:" + userId +":teams");
        if (cachedUserTeams != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            List<UserTeam> list = objectMapper.convertValue(
                    cachedUserTeams,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, UserTeam.class)
            );
            if (list.isEmpty()) {
                throw new RuntimeException("当前user没有加入任何team");
            }
            System.out.println("Cache hit: userId=" + userId);
            return list;
        }

        List<UserTeam> list = userTeamMapper.getUserTeams(userId);
        if (list.isEmpty()) {
            throw new RuntimeException("当前user没有加入任何team");
        }
        System.out.println("Cache miss: userId=" + userId);
        redisTemplate.opsForValue().set("user:" + userId + ":teams", list, 5, TimeUnit.MINUTES);
        return list;
    }

    @Override
    public UserTeam getMember(Long teamId, Long userId) {
        return userTeamMapper.getMember(teamId, userId);
    }

    @Override
    public void modifyUserRole(Long teamId, Long userId, Long targetUserId, String role) {
        UserTeam operator = userTeamMapper.getMember(teamId, userId);

        if (operator == null || !operator.getRole().equals("admin")) {
            throw new RuntimeException("当前操作者无权更改用户role");
        }

        UserTeam target = userTeamMapper.getMember(teamId, targetUserId);
        if (target == null) {
            throw new RuntimeException("更改目标用户不在team中");
        }

        userTeamMapper.modifyUserRole(targetUserId, role);
        redisTemplate.delete("team:" + teamId +":members");
    }


}
