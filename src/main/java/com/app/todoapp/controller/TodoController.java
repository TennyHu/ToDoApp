package com.app.todoapp.controller;

import com.app.todoapp.entity.*;
import com.app.todoapp.services.TodoServiceImpl;
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

    @GetMapping
    public Result getTasks() {
        List<ToDo> todos = todoServiceImpl.getAllTodo();
        return Result.success(todos);
    }

//    @GetMapping("/page")
//    public Result getTasksPagination(
//            @RequestParam(defaultValue = "1") int page, // page start from 1
//            @RequestParam(defaultValue = "10") int size, // 10 data each page
//            @RequestParam(defaultValue = "id") String orderBy
//
//    ) {
//        Page<Task> springPage = todoServiceImpl.getTasksWithPagination(page - 1, size, orderBy);
//        // convert into Page Bean
//        PageBean<Task> pageBean = new PageBean<>(springPage);
//        return Result.success(pageBean);
//    }

    @GetMapping("/{id}")
    public Result getTaskById(@PathVariable Long id){
        ToDo todo = todoServiceImpl.getTodoById(id);
        return Result.success(todo);
    }

    @PostMapping
    public Result createTask(@RequestBody ToDo toDo){
        todoServiceImpl.createTodo(toDo);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result toggleTask(@PathVariable Long id){
        todoServiceImpl.toggleTodo(id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result deleteTask(@PathVariable Long id){
        todoServiceImpl.deleteTodo(id);
        return Result.success();
    }

}
