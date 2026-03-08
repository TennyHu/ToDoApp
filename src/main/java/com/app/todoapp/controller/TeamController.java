package com.app.todoapp.controller;

import com.app.todoapp.entity.Result;
import com.app.todoapp.entity.Team;
import com.app.todoapp.services.implement.TeamServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamServiceImpl teamServiceImpl;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public TeamController(TeamServiceImpl teamServiceImpl, RedisTemplate<String, Object> redisTemplate) {
        this.teamServiceImpl = teamServiceImpl;
        this.redisTemplate = redisTemplate;
    }

    private Long getUserId(HttpServletRequest request) {
        return (Long) request.getAttribute("userId");
    }

    @PostMapping()
    public Result createTeam(@RequestParam String teamName,
                             HttpServletRequest request) {
        Long userId = getUserId(request);
        Team team = teamServiceImpl.createTeam(teamName, userId);
        return Result.success(team);
    }

    @GetMapping("/{id}")
    public Result getTeamById(@PathVariable Long id) {
        Team team = teamServiceImpl.getTeamById(id);
        return Result.success(team);
    }

    @PutMapping()
    public Result updateTeam(@RequestBody Team team,
                             HttpServletRequest request) {
        Long userId = getUserId(request);
        teamServiceImpl.updateTeam(team.getId(), userId, team);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result deleteTeam(@RequestBody Team team,
                             HttpServletRequest request) {
        Long userId = getUserId(request);
        teamServiceImpl.deleteTeam(team.getId(), userId, team);
        return Result.success();

    }
}
