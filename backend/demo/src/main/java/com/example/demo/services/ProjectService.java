package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProjectDto;
import com.example.demo.models.Project;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.utils.ProjectMapper;

@Service
public class ProjectService {
    private static final String PROJECT_NOT_FOUND = "Project not found";
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(ProjectMapper::toDto)
                .toList();
    }

    public ProjectDto getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(PROJECT_NOT_FOUND));
        return ProjectMapper.toDto(project);
    }

    public ProjectDto createProject(ProjectDto projectDto) {
        Project project = new Project();
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());

        Project savedProject = projectRepository.save(project);
        return ProjectMapper.toDto(savedProject);
    }

    public ProjectDto updateProject(Long id, ProjectDto projectDto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(PROJECT_NOT_FOUND));

        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());

        Project updatedProject = projectRepository.save(project);
        return ProjectMapper.toDto(updatedProject);
    }

    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException(PROJECT_NOT_FOUND);
        }
        projectRepository.deleteById(id);
    }
}
