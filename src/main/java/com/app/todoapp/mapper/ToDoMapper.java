package com.app.todoapp.mapper;

import com.app.todoapp.entity.ToDo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ToDoMapper {

    @Select("select * from todo")
    List<ToDo> getTasks();
 }
