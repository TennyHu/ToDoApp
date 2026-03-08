package com.app.todoapp.services.implement;

import com.app.todoapp.config.RedisConfig;
import com.app.todoapp.entity.Team;
import com.app.todoapp.entity.UserTeam;
import com.app.todoapp.mapper.TeamMapper;
import com.app.todoapp.mapper.UserTeamMapper;
import com.app.todoapp.services.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamMapper teamMapper;
    private final UserTeamMapper userTeamMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static final String TASK_CACHE_PREFIX = "team:";

    @Autowired
    public TeamServiceImpl (TeamMapper teamMapper, UserTeamMapper userTeamMapper) {
        this.teamMapper = teamMapper;
        this.userTeamMapper = userTeamMapper;
    }

    @Transactional
    public Team createTeam(String teamName, Long userId) {
        Team team = new Team();
        team.setName(teamName);
        team.setCreatedBy(userId);
        try {
            teamMapper.createTeam(team);
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("team名已存在");
        }

        UserTeam userTeam = new UserTeam();
        userTeam.setTeamId(team.getId());
        userTeam.setUserId(userId);
        userTeam.setRole("admin");
        userTeamMapper.joinTeam(userTeam);

        redisTemplate.delete(TASK_CACHE_PREFIX + team.getId());
        return team;
    }

    @Override
    public Team getTeamById(Long id) {

        Object cachedTeam = redisTemplate.opsForValue().get(TASK_CACHE_PREFIX + id);
        if (cachedTeam != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            Team team = objectMapper.convertValue(cachedTeam, Team.class);
            if (team == null) {
                throw new RuntimeException("该team不存在");
            }
            System.out.println("Cache hit: id=" + id);
            return team;
        }

        Team team = teamMapper.getTeamById(id);
        if (team == null) {
            throw new RuntimeException("该team不存在");
        }
        System.out.println("Cache miss: id=" + id);
        redisTemplate.opsForValue().set(TASK_CACHE_PREFIX + team.getId(), team, 5, TimeUnit.MINUTES);
        return team;
    }

    @Override
    public void updateTeam(Long teamId, Long userId, Team team) {
        UserTeam userTeam = userTeamMapper.getMember(teamId, userId);
        if (userTeam == null || !userTeam.getRole().equals("admin")) {
            throw new RuntimeException("无权限更新团队信息");
        }
        teamMapper.updateTeam(team);
        redisTemplate.delete(TASK_CACHE_PREFIX + teamId);
    }


    @Override
    public void deleteTeam(Long teamId, Long userId, Team team) {
        UserTeam userTeam = userTeamMapper.getMember(teamId, userId);
        if (userTeam == null || !userTeam.getRole().equals("admin")) {
            throw new RuntimeException("无权限解散团队");
        }
        teamMapper.deleteTeam(team);
        redisTemplate.delete(TASK_CACHE_PREFIX + teamId);
    }
}
