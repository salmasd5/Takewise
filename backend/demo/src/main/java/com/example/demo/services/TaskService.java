package com.example.demo.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateTaskRequestDto;
import com.example.demo.dto.TaskDto;
import com.example.demo.dto.UpdateTaskRequestDto;
import com.example.demo.models.Project;
import com.example.demo.models.Task;
import com.example.demo.models.User;
import com.example.demo.repositories.ProjectRepository;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.TaskMapper;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, 
                      UserRepository userRepository,
                      ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public TaskDto createTask(CreateTaskRequestDto requestDto) {
        Task task = new Task();
        task.setTitle(requestDto.getTitle());
        task.setDescription(requestDto.getDescription());
        task.setStatus(requestDto.getStatus());
        task.setCompleted(requestDto.isCompleted());
        
        // Find and set the user
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);
        
        // Set project if provided
        if (requestDto.getProjectId() != null) {
            Project project = projectRepository.findById(requestDto.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            task.setProject(project);
        }
        
        Task savedTask = taskRepository.save(task);
        return TaskMapper.toDto(savedTask);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Set<TaskDto> getTasksByUserId(Long userId) {
        List<Task> tasks = taskRepository.findByUser_Id(userId);
        return tasks.stream()
                   .map(TaskMapper::toDto)
                   .collect(Collectors.toSet());
    }

    public Task updateTask(Long id, UpdateTaskRequestDto requestDto) {
        Task existingTask = taskRepository.findById(id).orElse(null);
        if (existingTask == null) {
            return null;
        }

        existingTask.setTitle(requestDto.getTitle());
        existingTask.setDescription(requestDto.getDescription());
        existingTask.setStatus(requestDto.getStatus());
        
        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> findAllCompletedTasks() {
        return taskRepository.findByCompletedTrue();
    }

    public List<Task> findAllIncompleteTasks() {
        return taskRepository.findByCompletedFalse();
    }
}
