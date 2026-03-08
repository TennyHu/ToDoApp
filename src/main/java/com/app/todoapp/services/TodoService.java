package com.app.todoapp.services;

import com.app.todoapp.entity.ToDo;

import java.util.List;

public interface TodoService {

    // 权限 - 当前用户
    // 操作 - 当前用户的所有to do
    List<ToDo> getAllTodo(Long userId);

    // 权限 - 当前用户
    // 操作 - 当前用户指定id的to do
    ToDo getTodoById(Long id, Long userId);

    // 权限 - 当前用户
    // 操作 - 当前用户的所有to do，分页查询
    List<ToDo> getTodoWithPagination(Long userId, String sortBy, int size, int page);

    // 权限 - 当前用户
    // 操作 - 创建属于当前用户的to do
    ToDo createTodo(ToDo todo, Long userId);


    // 权限 - 当前用户
    // 操作 - 删除当前用户指定id的to do
    void deleteTodo(Long id, Long userId);

    // 权限 - 当前用户
    // 操作 - 改变当前用户指定id的to do完成状态
    void toggleTodo(Long id, Long userId);


}
