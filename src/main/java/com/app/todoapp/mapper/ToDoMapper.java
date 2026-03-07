package com.app.todoapp.mapper;

import com.app.todoapp.entity.ToDo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ToDoMapper {

    // Retrieve - get all to do
    @Select("select * from todo where owner_id = #{userId}")
    List<ToDo> getAllTodo(Long userId);

    // Retrieve - get to do by id
    @Select("select * from todo where id = #{id}")
    Optional<ToDo> getTodoById(Long id);

    // Retrieve - get to do with pagination by id
    @Select("select * from todo where owner_id = #{ownerId} order by ${sortBy} desc limit #{size} offset #{offset}")
    List<ToDo> getTodoWithPagination(
            @Param("ownerId") Long ownerId,
            @Param("sortBy") String sortBy,
            @Param("size") int size,
            @Param("offset") int offset);

    // Create - create a to do
    @Insert("insert into todo (completed, title, priority, deadline, owner_id)" +
            "values (#{completed}, #{title}, #{priority}, #{deadline}, #{ownerId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")  // 把自动生成的id回填到对象里
    int createTodo(ToDo todo);

    // Update - update to do complete status
    @Update("update todo set (completed = not completed) where id = #{id}")
    void toggleTodo(Long id);

    // Delete - delete to do by id
    @Delete("delete from todo where id = #{id}")
    void deleteTodo(Long id);


 }
