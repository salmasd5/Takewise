package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateTaskRequestDto {
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    @NotBlank(message = "Status is required")
    private String status;
    
    @NotNull(message = "User ID is required")
    private Long userId;

    private Long projectId;  // Optional project ID
    
    private boolean completed = false;  // Default to false

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public Long getUserId() { return userId; }
    public Long getProjectId() { return projectId; }
    public boolean isCompleted() { return completed; }

    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(String status) { this.status = status; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
