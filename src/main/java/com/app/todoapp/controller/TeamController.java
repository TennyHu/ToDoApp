package com.app.todoapp.controller;

import com.app.todoapp.entity.Result;
import com.app.todoapp.entity.Team;
import com.app.todoapp.services.implement.TeamServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
public class TeamController {


    private final TeamServiceImpl teamServiceImpl;

    @Autowired
    public TeamController(TeamServiceImpl teamServiceImpl) {
        this.teamServiceImpl = teamServiceImpl;
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

    @PutMapping("/{id}")
    public Result updateTeam(@RequestBody Team team,
                             HttpServletRequest request) {
        Long userId = getUserId(request);
        if (!team.getId().equals(userId)) {
            throw new RuntimeException("无权更改团队信息");
        }

        teamServiceImpl.updateTeam(team.getId(), userId, team);

        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result deleteTeam(@RequestBody Team team,
                             HttpServletRequest request) {
        Long userId = getUserId(request);
        if (!team.getCreatedBy().equals(userId)) {
            throw new RuntimeException("无权更改团队信息");
        }

        teamServiceImpl.deleteTeam(team.getId(), userId, team);

        return Result.success();

    }
}
