package com.example.demo.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;

public class TeamDto {
    private Long id;
    
    @NotBlank(message = "Team name is required")
    private String name;
    
    private String description;
    private Set<Long> memberIds;

    // Constructor
    public TeamDto(Long id, String name, String description, Set<Long> memberIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.memberIds = memberIds;
    }

    // Default constructor
    public TeamDto() {}

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Set<Long> getMemberIds() { return memberIds; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setMemberIds(Set<Long> memberIds) { this.memberIds = memberIds; }
} 