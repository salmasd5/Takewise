package com.example.demo.dto;

public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private String status;
    private boolean completed;
    private Long userId;
    private Long projectId;

    // Constructor
    public TaskDto(Long id, String title, String description, String status, 
                  boolean completed, Long userId, Long projectId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.completed = completed;
        this.userId = userId;
        this.projectId = projectId;
    }

    // Default constructor
    public TaskDto() {}

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public boolean isCompleted() { return completed; }
    public Long getUserId() { return userId; }
    public Long getProjectId() { return projectId; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(String status) { this.status = status; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
}
