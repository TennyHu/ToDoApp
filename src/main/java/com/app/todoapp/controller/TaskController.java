package com.app.todoapp.controller;

import com.app.todoapp.models.Priority;
import com.app.todoapp.models.Task;
import com.app.todoapp.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/todotasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String getTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @PostMapping
    public String createTask(@RequestParam String title, @RequestParam Priority priority, LocalDateTime deadline) {
        taskService.createTask(title, priority, deadline);
        return "redirect:/todotasks";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> toggleTask(@PathVariable Long id) {
        taskService.toggleTask(id);
        return ResponseEntity.ok().build();
    }
}
