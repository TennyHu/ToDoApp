package com.app.todoapp.services;

import com.app.todoapp.models.Priority;
import com.app.todoapp.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();

    Task createTask(String title, Priority priority, LocalDate deadline);

    void deleteTask(Long id);

    void toggleTask(Long id);

    Task getTaskById(Long id);

    Page<Task> getTasksWithPagination(int page, int size, String sortBy);
}
