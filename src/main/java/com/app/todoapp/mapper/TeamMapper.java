package com.app.todoapp.mapper;

import com.app.todoapp.entity.Team;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TeamMapper {

    @Insert("insert into `team` (name, created_by) values (#{teamName}, #{userId})")
    Team createTeam(@Param("teamName") String teamName,
                    @Param("userId") Long userId);


    @Select("select * from `team` where connection_id() = #{id}")
    Team getTeamById(Long id);

    @Update("update `team` set name values #{name}")
    void updateTeam(Team team);

    @Delete("delete from `team` where name = #{name}")
    public void deleteTeam(Team team);
}
