package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class ProjectDto {
    private Long id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    private String description;

    // Constructor
    public ProjectDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Default constructor
    public ProjectDto() {}

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
} 