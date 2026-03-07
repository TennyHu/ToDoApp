package com.app.todoapp.services;

import com.app.todoapp.entity.ToDo;

import java.util.List;

public interface TodoService {
    List<ToDo> getAllTodo();

    ToDo getTodoById(Long id);

    List<ToDo> getTodoWithPagination(Long ownerId, String sortBy, int size, int page);

    ToDo createTodo(ToDo todo);

    void deleteTodo(Long id);

    void toggleTodo(Long id);

//    Page<Task> getTasksWithPagination(int page, int size, String sortBy);
}
