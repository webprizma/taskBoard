package com.example.demo.service;

import com.example.demo.entity.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TaskService {
    Task saveTask(Task task);
    List<Task> fetchTaskList();
    Optional<Task> fetchTaskById(Long taskId);
    Task updateTask(Task task, Long taskId);
    void deleteTaskById(Long taskId);
}
