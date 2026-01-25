package com.app.todoapp.controller;

import com.app.todoapp.models.Priority;
import com.app.todoapp.models.Result;
import com.app.todoapp.models.Task;
import com.app.todoapp.services.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")     // 允许前端为不同端口

public class TaskApiController {
    @Autowired
    private TaskServiceImpl taskServiceImpl;

    public TaskApiController(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }

    @GetMapping
    public Result getTasks() {
        List<Task> tasks = taskServiceImpl.getAllTasks();
        return Result.success(tasks);
    }

    @GetMapping("{id}")
    public Result getTaskById(@PathVariable Long id){
        Task task = taskServiceImpl.getTaskById(id);
        return Result.success(task);
    }

    @PostMapping
    public Result createTask(@RequestParam String title,
                             @RequestParam Priority priority, @RequestParam LocalDate deadline){
        taskServiceImpl.createTask(title, priority, deadline);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result toggleTask(@PathVariable Long id){
        taskServiceImpl.toggleTask(id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result deleteTask(@PathVariable Long id){
        taskServiceImpl.deleteTask(id);
        return Result.success();
    }

}
