package com.app.todoapp.services;

import com.app.todoapp.entity.Priority;
import com.app.todoapp.entity.Task;
import com.app.todoapp.mapper.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private final TaskRepository taskRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static final String TASK_CACHE_KEY = "tasks:all";
    private static final String TASK_CACHE_PREFIX = "task:";

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAllTasks() {

        // check cache
        List<Task> cachedTasks = (List<Task>) redisTemplate.opsForValue().get(TASK_CACHE_KEY);
        if (cachedTasks != null) {
            System.out.println("Accessing tasks from cache");
            return cachedTasks;
        }

        // no cache, search database
        System.out.println("Accessing tasks from database");
        List<Task> taskList = taskRepository.findAll();

        // write in cache, limit 5min
        redisTemplate.opsForValue().set(TASK_CACHE_KEY, taskList, 5, TimeUnit.MINUTES);

        return taskList;
    }

    @Override
    public Page<Task> getTasksWithPagination(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return taskRepository.findAll(pageable);
    }

    @Override
    public Task getTaskById(Long id) {
        String cacheKey = TASK_CACHE_PREFIX + id;

        // check cache
        Task cacheTask = (Task) redisTemplate.opsForValue().get(cacheKey);
        if (cacheTask != null) {
            System.out.println("Accessing task from cache:" + id);
            return cacheTask;
        }

        // check database
        System.out.println("Accessing task from database:" + id);
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));

        // write in chache, limit 10min
        if (task != null) {
            redisTemplate.opsForValue().set(TASK_CACHE_PREFIX + id, task, 10, TimeUnit.MINUTES);
        }

        return task;
    }

    @Override
    public Task createTask(String title, Priority priority, LocalDate deadline) {
        Task task = new Task();
        task.setTitle(title);
        task.setCompleted(false);
        task.setPriority(priority);
        task.setDeadline(deadline);
        if (priority == null) {
            priority = Priority.LOW;
        }

        Task savedTask = taskRepository.save(task);

        // clean related cache
        redisTemplate.delete(TASK_CACHE_KEY);

        return savedTask;
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);

        // clean related cache
        redisTemplate.delete(TASK_CACHE_PREFIX + id);
        redisTemplate.delete(TASK_CACHE_PREFIX + id);

    }

    @Override
    public void toggleTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (task != null) {
            task.setCompleted(!task.isCompleted());
            taskRepository.save(task);

            // clean related cache
            redisTemplate.delete(TASK_CACHE_PREFIX + id);
            redisTemplate.delete(TASK_CACHE_KEY);
        }



    }


}
