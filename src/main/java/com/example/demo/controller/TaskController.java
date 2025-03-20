package com.example.demo.controller;

import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/tasks")
    public Task saveTask(@Valid @RequestBody Task task) {
        return taskService.saveTask(task);
    }

    @GetMapping("/tasks")
    public List<Task> fetchTaskList()
    {
        return taskService.fetchTaskList();
    }


    @PutMapping("/tasks/{id}")
    public Task updateTask(@RequestBody Task task, @PathVariable("id") Long taskId)
    {
        return taskService.updateTask(task, taskId);
    }

    @DeleteMapping(value="/{id}")
    public void deleteTask(@PathVariable("id") Long id){
        taskService.deleteTaskById(id);
    }
}
