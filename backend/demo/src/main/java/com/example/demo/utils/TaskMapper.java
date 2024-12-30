package com.example.demo.utils;

import com.example.demo.dto.TaskDto;
import com.example.demo.models.Task;

public class TaskMapper {
    private TaskMapper() {
        // Private constructor to hide implicit public one
    }

    public static TaskDto toDto(Task task) {
        if (task == null) return null;
        return new TaskDto(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getStatus(),
            task.isCompleted(),
            task.getUser() != null ? task.getUser().getId() : null,
            task.getProject() != null ? task.getProject().getId() : null
        );
    }
} 