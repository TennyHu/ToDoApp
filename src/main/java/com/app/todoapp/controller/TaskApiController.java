package com.app.todoapp.controller;

import com.app.todoapp.models.PageBean;
import com.app.todoapp.models.Priority;
import com.app.todoapp.models.Result;
import com.app.todoapp.models.Task;
import com.app.todoapp.services.TaskServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/todotasks")
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

    @GetMapping("/page")
    public Result getTasksPagination(
            @RequestParam(defaultValue = "1") int page, // page start from 1
            @RequestParam(defaultValue = "10") int size, // 10 data each page
            @RequestParam(defaultValue = "id") String orderBy

    ) {
        Page<Task> springPage = taskServiceImpl.getTasksWithPagination(page - 1, size, orderBy);
        // convert into Page Bean
        PageBean<Task> pageBean = new PageBean<>(springPage);
        return Result.success(pageBean);
    }

    @GetMapping("/{id}")
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
