package com.app.todoapp.mapper;

import com.app.todoapp.entity.Team;
import com.app.todoapp.entity.UserTeam;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.AdviceModeImportSelector;

import java.util.List;

@Mapper
public interface UserTeamMapper {

    @Insert("insert into `user_team` (user_id, team_id, role) values (#{userId}, #{teamId}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int joinTeam(UserTeam userTeam);


    @Delete("delete from `user_team` where user_id = #{userId} and team_id = #{teamId}")
    void leaveTeam(@Param("teamId") Long teamId,
                   @Param("userId") Long userId);


    @Select("select * from `user_team` where team_id = #{teamId}")
    List<UserTeam> getTeamUsers(Long teamId);


    @Select("select * from `user_team` where user_id = #{userId}")
    List<UserTeam> getUserTeams(Long userId);


    @Select("select * from `user_team` where team_id = #{teamId} and user_id = #{userId}")
    UserTeam getMember(@Param("teamId") Long teamId,
                       @Param("userId") Long userId);


    @Update("update `user_team` set role = #{role} where user_id = #{userId}")
    void modifyUserRole(@Param("userId") Long userId,
                        @Param("role") String role);


    @Select("select count(*) from `user_team` where team_id = #{teamId} and role = 'admin'")
    int countAdmins(Long teamId);
}
