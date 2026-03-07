package com.app.todoapp.controller;

import com.app.todoapp.entity.Result;
import com.app.todoapp.entity.Team;
import com.app.todoapp.services.TeamService;
import com.app.todoapp.services.TeamServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamServiceImpl teamServiceImpl;

    public TeamController(TeamServiceImpl teamServiceImpl) {
        this.teamServiceImpl = teamServiceImpl;
    }

    private Long getUserId(HttpServletRequest request) {
        return (Long) request.getAttribute("userId");
    }

    @PutMapping()
    public Result createTeam(String teamName, HttpServletRequest request) {
        Long userId = getUserId(request);
        Team team = teamServiceImpl.createTeam(teamName, userId);
        return Result.success(team);
    }

    @GetMapping("/{id}")
    public Result getTeamById(Long id) {
        Team team = teamServiceImpl.getTeamById(id);
        return Result.success(team);
    }

    @PutMapping("{id}")
    public Result updateTeam(Team team, HttpServletRequest request) {
        Long userId = getUserId(request);
        if (!team.getId().equals(userId)) {
            throw new RuntimeException("无权更改团队信息");
        }

        teamServiceImpl.updateTeam(team);

        return Result.success();
    }

    @DeleteMapping("{id}")
    public Result deleteTeam(Team team, HttpServletRequest request) {
        Long userId = getUserId(request);
        if (!team.getCreatedBy().equals(userId)) {
            throw new RuntimeException("无权更改团队信息");
        }

        teamServiceImpl.deleteTeam(team);

        return Result.success();

    }
}
