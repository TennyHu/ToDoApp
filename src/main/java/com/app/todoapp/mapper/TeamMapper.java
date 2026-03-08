package com.app.todoapp.mapper;

import com.app.todoapp.entity.Team;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TeamMapper {

    @Insert("insert into `team` (name, created_by) values (#{name}, #{createdBy})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int createTeam(Team team);


    @Select("select * from `team` where id = #{id}")
    Team getTeamById(Long id);


    @Update("update `team` set name values #{name}")
    void updateTeam(Team team);


    @Delete("delete from `team` where name = #{name}")
    public void deleteTeam(Team team);
}
