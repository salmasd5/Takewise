package com.example.demo.testunitaire;

package com.example.demo.controllers;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.dto.CreateTaskRequestDto;
import com.example.demo.dto.TaskDto;
import com.example.demo.dto.UpdateTaskRequestDto;
import com.example.demo.models.Task;
import com.example.demo.services.TaskService;
import com.example.demo.utils.TaskMapper;

public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    public TaskControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTasksByUserId_Found() {
        // Arrange
        Long userId = 1L;
        Set<TaskDto> mockTasks = new HashSet<>();
        mockTasks.add(new TaskDto(1L, "Test Task", "Description", "OPEN", false, null, null));

        when(taskService.getTasksByUserId(userId)).thenReturn(mockTasks);

        // Act
        ResponseEntity<Set<TaskDto>> response = taskController.getTasksByUserId(userId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void testGetTasksByUserId_NotFound() {
        // Arrange
        Long userId = 1L;
        when(taskService.getTasksByUserId(userId)).thenReturn(Collections.emptySet());

        // Act
        ResponseEntity<Set<TaskDto>> response = taskController.getTasksByUserId(userId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testGetTaskById_Found() {
        // Arrange
        Long taskId = 1L;
        Task mockTask = new Task();
        mockTask.setId(taskId);
        mockTask.setTitle("Test Task");
        when(taskService.getTaskById(taskId)).thenReturn(mockTask);

        // Act
        ResponseEntity<TaskDto> response = taskController.getTaskById(taskId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(taskId);
    }

    @Test
    public void testGetTaskById_NotFound() {
        // Arrange
        Long taskId = 1L;
        when(taskService.getTaskById(taskId)).thenReturn(null);

        // Act
        ResponseEntity<TaskDto> response = taskController.getTaskById(taskId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testCreateTask() {
        // Arrange
        CreateTaskRequestDto requestDto = new CreateTaskRequestDto("Test Task", "Description", "OPEN", false, null, null);
        TaskDto createdTask = new TaskDto(1L, "Test Task", "Description", "OPEN", false, null, null);

        when(taskService.createTask(requestDto)).thenReturn(createdTask);

        // Act
        ResponseEntity<TaskDto> response = taskController.createTask(requestDto);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Test Task");
    }

    @Test
    public void testUpdateTask_Found() {
        // Arrange
        Long taskId = 1L;
        UpdateTaskRequestDto requestDto = new UpdateTaskRequestDto("Updated Task", "Updated Description", "IN_PROGRESS");
        Task updatedTask = new Task();
        updatedTask.setId(taskId);
        updatedTask.setTitle("Updated Task");

        when(taskService.updateTask(taskId, requestDto)).thenReturn(updatedTask);

        // Act
        ResponseEntity<TaskDto> response = taskController.updateTask(taskId, requestDto);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Updated Task");
    }

    @Test
    public void testUpdateTask_NotFound() {
        // Arrange
        Long taskId = 1L;
        UpdateTaskRequestDto requestDto = new UpdateTaskRequestDto("Updated Task", "Updated Description", "IN_PROGRESS");
        when(taskService.updateTask(taskId, requestDto)).thenReturn(null);

        // Act
        ResponseEntity<TaskDto> response = taskController.updateTask(taskId, requestDto);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testDeleteTask() {
        // Arrange
        Long taskId = 1L;
        doNothing().when(taskService).deleteTask(taskId);

        // Act
        ResponseEntity<Void> response = taskController.deleteTask(taskId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(taskService, times(1)).deleteTask(taskId);
    }
}
