package com.app.todoapp.controller;

import com.app.todoapp.entity.*;
import com.app.todoapp.services.TodoServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/todotasks")
@CrossOrigin(origins = "*")     // 允许前端为不同端口

public class TodoController {

    private TodoServiceImpl todoServiceImpl;

    @Autowired
    public TodoController(TodoServiceImpl todoServiceImpl) {
        this.todoServiceImpl = todoServiceImpl;
    }

    private Long getUserId(HttpServletRequest request) {
        return (Long) request.getAttribute("userId");
    }

    @GetMapping
    public Result getAllTodo(HttpServletRequest request) {
        Long userId = getUserId(request);
        List<ToDo> todos = todoServiceImpl.getAllTodo(userId);
        return Result.success(todos);
    }

    @GetMapping("/{id}")
    public Result getTodoById(@PathVariable Long id, HttpServletRequest request) {
        Long userId = getUserId(request);
        ToDo todo = todoServiceImpl.getTodoById(id, userId);
        return Result.success(todo);
    }

    @GetMapping("/page")
    public Result getTodoWithPagination(
            @RequestParam Long ownerId,
            @RequestParam(defaultValue = "priority") String sortBy,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<ToDo> todos = todoServiceImpl.getTodoWithPagination(ownerId, sortBy, page, size);
        return Result.success(todos);
    }

    @PostMapping
    public Result createTask(@RequestBody ToDo toDo, HttpServletRequest request){
        Long userId = getUserId(request);
        ToDo toDo1 = todoServiceImpl.createTodo(toDo, userId);
        return Result.success(toDo1);
    }

    @DeleteMapping("/{id}")
    public Result deleteTask(@PathVariable Long id, HttpServletRequest request){
        Long userId = getUserId(request);
        todoServiceImpl.deleteTodo(id, userId);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result toggleTask(@PathVariable Long id, HttpServletRequest request){
        Long userId = getUserId(request);
        todoServiceImpl.toggleTodo(id, userId);
        return Result.success();
    }

}
