package com.app.todoapp.services;

import com.app.todoapp.entity.ToDo;
import com.app.todoapp.mapper.ToDoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TodoServiceImpl implements TodoService {


    private final ToDoMapper toDoMapper;

    @Autowired
    public TodoServiceImpl(ToDoMapper toDoMapper) {
        this.toDoMapper = toDoMapper;
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static final String TASK_CACHE_KEY = "tasks:all";
    private static final String TASK_CACHE_PREFIX = "task:";

    @Override
    public List<ToDo> getAllTodo() {

        // check cache
        List<ToDo> cachedTasks = (List<ToDo>) redisTemplate.opsForValue().get(TASK_CACHE_KEY);
        if (cachedTasks != null) {
            System.out.println("Accessing todo from cache");
            return cachedTasks;
        }

        // no cache, search database
        System.out.println("Accessing todo from database");
        List<ToDo> taskList = toDoMapper.getAllTodo();

        // write in cache, limit 5min
        redisTemplate.opsForValue().set(TASK_CACHE_KEY, taskList, 5, TimeUnit.MINUTES);

        return taskList;
    }

//    @Override
//    public Page<Task> getTasksWithPagination(int page, int size, String sortBy) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
//        return toDoMapper.getAllTodo(pageable);
//    }

    @Override
    public ToDo getTodoById(Long id) {
        String cacheKey = TASK_CACHE_PREFIX + id;

        // check cache
        ToDo cacheTask = (ToDo) redisTemplate.opsForValue().get(cacheKey);
        if (cacheTask != null) {
            System.out.println("Accessing todo from cache:" + id);
            return cacheTask;
        }

        // check database
        System.out.println("Accessing todo from database:" + id);
        ToDo toDo = toDoMapper.getTodoById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        // write in chache, limit 10min
        if (toDo != null) {
            redisTemplate.opsForValue().set(TASK_CACHE_PREFIX + id, toDo, 10, TimeUnit.MINUTES);
        }

        return toDo;
    }

    @Override
    public ToDo createTodo(ToDo toDo) {
        toDoMapper.createTodo(toDo);

        // clean related cache
        redisTemplate.delete(TASK_CACHE_KEY);

        return toDo;
    }

    @Override
    public void deleteTodo(Long id) {
        toDoMapper.deleteTodo(id);

        // clean related cache
        redisTemplate.delete(TASK_CACHE_PREFIX + id);
        redisTemplate.delete(TASK_CACHE_KEY);
    }

    @Override
    public void toggleTodo(Long id) {
        ToDo toDo = toDoMapper.getTodoById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found"));

        if (toDo != null) {
            toDoMapper.toggleTodo(id);

            // clean related cache
            redisTemplate.delete(TASK_CACHE_PREFIX + id);
            redisTemplate.delete(TASK_CACHE_KEY);
        }



    }


}
