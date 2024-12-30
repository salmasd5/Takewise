package com.example.demo.dto;

public class UpdateTaskRequestDto {
    private String title;
    private String description;
    private String status;

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }

    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(String status) { this.status = status; }
}
