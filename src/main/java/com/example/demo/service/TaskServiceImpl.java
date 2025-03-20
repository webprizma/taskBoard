package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> fetchTaskList() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> fetchTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public Task updateTask(Task task, Long taskId) {
        Task taskDB = taskRepository.findById(taskId).get();

        if (Objects.nonNull(task.getTitle()) && !"".equalsIgnoreCase(task.getTitle())) {
            taskDB.setTitle(task.getTitle());
        }

        if (Objects.nonNull(task.getDescription())) {
            taskDB.setDescription(task.getDescription());
        }

        taskDB.setActive(task.getActive());

        return taskRepository.save(taskDB);
    }

    @Override
    public void deleteTaskById(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
