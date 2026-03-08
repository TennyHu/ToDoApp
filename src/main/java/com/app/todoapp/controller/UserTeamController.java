package com.app.todoapp.controller;

import com.app.todoapp.entity.Result;
import com.app.todoapp.entity.UserTeam;
import com.app.todoapp.services.implement.UserTeamServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserTeamController {


    private final UserTeamServiceImpl userTeamService;

    @Autowired
    public UserTeamController(UserTeamServiceImpl userTeamService) {
        this.userTeamService = userTeamService;

    }

    private Long getUserId(HttpServletRequest request) {
        return (Long) request.getAttribute("userId");
    }

    @PostMapping("/team/{teamId}/members")
    public Result joinTeam(@PathVariable Long teamId,
                           HttpServletRequest request) {
        Long userId = getUserId(request);
        userTeamService.joinTeam(teamId, userId);
        return Result.success();
    }

    @DeleteMapping("/team/{teamId}/members")
    public Result leaveTeam(@PathVariable Long teamId,
                            HttpServletRequest request) {
        Long userId = getUserId(request);
        userTeamService.leaveTeam(teamId, userId);
        return Result.success();
    }

    @GetMapping("/team/{teamId}/members")
    public List<UserTeam> getTeamUsers(@PathVariable Long teamId) {
        return userTeamService.getTeamUsers(teamId);
    }

    @GetMapping("/user/teams")
    public List<UserTeam> getUserTeams(@PathVariable Long userId) {
        return userTeamService.getUserTeams(userId);
    }

    @PutMapping("/team/update")
    public Result modifyUserRole(@RequestParam Long teamId,
                                 @RequestParam Long targetUserId,
                                 @RequestParam String role,
                                 HttpServletRequest request) {
        Long userId = getUserId(request);
        userTeamService.modifyUserRole(teamId, userId, targetUserId, role);
        return Result.success();
    }




}
