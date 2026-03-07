package com.app.todoapp.services;

import com.app.todoapp.entity.Team;
import com.app.todoapp.mapper.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private final TeamMapper teamMapper;

    public TeamServiceImpl (TeamMapper teamMapper) {
        this.teamMapper = teamMapper;
    }

    @Transactional
    public Team createTeam(String teamName, Long userId) {
        Team team = teamMapper.createTeam(teamName, userId);

        return team;
    }

    @Override
    public Team getTeamById(Long id) {
        return teamMapper.getTeamById(id);
    }


    public void updateTeam(Team team) {
        teamMapper.updateTeam(team);
    }


    public void deleteTeam(Team team) {
        teamMapper.deleteTeam(team);
    }
}
