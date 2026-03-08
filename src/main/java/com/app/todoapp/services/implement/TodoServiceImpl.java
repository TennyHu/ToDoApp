package com.app.todoapp.services.implement;

import com.app.todoapp.entity.ToDo;
import com.app.todoapp.mapper.ToDoMapper;
import com.app.todoapp.services.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
    private static final String TASK_ALL_PREFIX = "tasks:user:"; // + userId
    private static final String TASK_CACHE_PREFIX = "task:";     // + id

    @Override
    public List<ToDo> getAllTodo(Long userId) {
        String cacheKey = TASK_ALL_PREFIX + userId;

        // 检查缓存
        Object cachedTasks = redisTemplate.opsForValue().get(cacheKey);
        if (cachedTasks != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            List<ToDo> cachedToDo = objectMapper.convertValue(
                    cachedTasks,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ToDo.class)
                    );
            System.out.println("Cache hit: userId=" + userId);
            return cachedToDo;
        }

        // 缓存miss
        System.out.println("Cache miss: userId=" + userId);
        List<ToDo> taskList = toDoMapper.getAllTodo(userId);

        // 写入缓存，TTL = 5min
        redisTemplate.opsForValue().set(cacheKey, taskList, 5, TimeUnit.MINUTES);

        return taskList;
    }

    @Override
    public List<ToDo> getTodoWithPagination(Long ownerId, String sortBy, int page, int size) {
        String cacheKey = TASK_ALL_PREFIX + ownerId + ":page:" + page + ":size:" + size + ":sortBy:" + sortBy;

        Object cachedTasks = redisTemplate.opsForValue().get(cacheKey);
        if (cachedTasks != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            List<ToDo> cachedToDo = objectMapper.convertValue(
                    cachedTasks,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ToDo.class)
            );
            System.out.println("Cache hit: ownerId=" + ownerId);
            return cachedToDo;
        }

        int offset = (page - 1) * size;
        List<ToDo> toDos = toDoMapper.getTodoWithPagination(ownerId, sortBy, size, offset);

        redisTemplate.opsForValue().set(cacheKey, toDos, 5, TimeUnit.MINUTES);
        System.out.println("Cache miss: ownerId=" + ownerId);
        return toDos;
    }

    @Override
    public ToDo getTodoById(Long id, Long userId) {
        String cacheKey = TASK_CACHE_PREFIX + id;

        Object cacheTask = redisTemplate.opsForValue().get(cacheKey);
        if (cacheTask != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            ToDo cacheToDo = objectMapper.convertValue(cacheTask, ToDo.class);
            if (!cacheToDo.getOwnerId().equals(userId)) {
                throw new RuntimeException("无权访问");
            }
            System.out.println("Cache hit: id=" + id);
            return cacheToDo;
        }

        // check database
        System.out.println("Cache miss: id=" +id);
        ToDo toDo1 = toDoMapper.getTodoById(id)
                .orElseThrow(() -> new RuntimeException("未找到待办事项"));
        if (!toDo1.getOwnerId().equals(userId)) {
            throw new RuntimeException("无权访问");
        }

        redisTemplate.opsForValue().set(cacheKey, toDo1, 10, TimeUnit.MINUTES);

        return toDo1;
    }

    @Override
    public ToDo createTodo(ToDo todo, Long userId) {
        todo.setOwnerId(userId);

        toDoMapper.createTodo(todo);
        redisTemplate.delete(TASK_ALL_PREFIX + userId);
        return todo;
    }

    @Override
    public void deleteTodo(Long id, Long userId) {
        ToDo toDo = toDoMapper.getTodoById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found"));
        if (!toDo.getOwnerId().equals(userId)) {
            throw new RuntimeException("无权访问");
        }

        toDoMapper.deleteTodo(id);
        redisTemplate.delete(TASK_CACHE_PREFIX + id);
        redisTemplate.delete(TASK_ALL_PREFIX + userId);
    }

    @Override
    public void toggleTodo(Long id, Long userId) {
        ToDo toDo = toDoMapper.getTodoById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found"));
        if (!toDo.getOwnerId().equals(userId)) {
            throw new RuntimeException("无权访问");
        }

        toDoMapper.toggleTodo(id);
        redisTemplate.delete(TASK_CACHE_PREFIX + id);
        redisTemplate.delete(TASK_ALL_PREFIX + userId);
    }

}



