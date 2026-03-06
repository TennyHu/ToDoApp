package com.app.todoapp.mapper;

import com.app.todoapp.entity.ToDo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ToDoMapper {

    @Select("select * from todo")
    List<ToDo> getAllTodo();

    @Select("select * from todo where id = #{id}")
    Optional<ToDo> getTodoById(Long id);

    @Insert("insert into todo (completed, title, priority, deadline, owner_id)" +
            "values (#{completed}, #{title}, #{priority}, #{deadline}, #{ownerId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")  // 把自动生成的id回填到对象里
    int createTodo(ToDo todo);

    @Update("update todo set (completed = not completed) where id = #{id}")
    void toggleTodo(Long id);

    @Delete("delete from todo where id = #{id}")
    void deleteTodo(Long id);
 }
