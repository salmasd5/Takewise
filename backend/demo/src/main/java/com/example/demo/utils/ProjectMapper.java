package com.example.demo.utils;

import com.example.demo.dto.ProjectDto;
import com.example.demo.models.Project;

public class ProjectMapper {
    private ProjectMapper() {
        // Private constructor to hide implicit public one
    }

    public static ProjectDto toDto(Project project) {
        if (project == null) return null;
        return new ProjectDto(
            project.getId(),
            project.getName(),
            project.getDescription()
        );
    }
} 