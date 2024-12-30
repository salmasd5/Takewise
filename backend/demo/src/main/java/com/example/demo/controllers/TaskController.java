package com.example.demo.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CreateTaskRequestDto;
import com.example.demo.dto.TaskDto;
import com.example.demo.dto.UpdateTaskRequestDto;
import com.example.demo.models.Task;
import com.example.demo.services.TaskService;
import com.example.demo.utils.TaskMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Get all tasks by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Set<TaskDto>> getTasksByUserId(@PathVariable Long userId) {
        Set<TaskDto> tasks = taskService.getTasksByUserId(userId);
        if (tasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(tasks);
    }

    // Get a task by its ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return task != null ? ResponseEntity.ok(TaskMapper.toDto(task))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Create a new task
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody CreateTaskRequestDto requestDto) {
        TaskDto createdTask = taskService.createTask(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    // Update an existing task
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id,
                                              @RequestBody UpdateTaskRequestDto requestDto) {
        Task task = taskService.updateTask(id, requestDto);
        return task != null ? ResponseEntity.ok(TaskMapper.toDto(task))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Delete a task by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
