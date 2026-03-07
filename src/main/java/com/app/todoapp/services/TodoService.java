package com.app.todoapp.services;

import com.app.todoapp.entity.ToDo;

import java.util.List;

public interface TodoService {

    // 只返回当前用户的all to do
    List<ToDo> getAllTodo(Long userId);

    // 查到后要验证当前to do是否属于当前用户的
    ToDo getTodoById(Long id, Long userId);

    List<ToDo> getTodoWithPagination(Long userId, String sortBy, int size, int page);

    // 新建to do属于当前用户，传入to do不知道user Id, 要通过登录从当前用户token中传入
    ToDo createTodo(ToDo todo, Long userId);

    // 删to do之前验证是否是自己的
    void deleteTodo(Long id, Long userId);

    // toggle to do之前验证是否是自己的
    void toggleTodo(Long id, Long userId);

}
