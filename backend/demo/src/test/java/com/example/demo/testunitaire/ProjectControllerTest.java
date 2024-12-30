package com.example.demo.testunitaire;

import com.example.demo.dto.ProjectDto;
import com.example.demo.services.ProjectService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Test
    public void testGetAllProjects() throws Exception {
        List<ProjectDto> projects = Arrays.asList(
                new ProjectDto(1L, "Project 1", "Description 1"),
                new ProjectDto(2L, "Project 2", "Description 2")
        );

        when(projectService.getAllProjects()).thenReturn(projects);

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Project 1")))
                .andExpect(jsonPath("$[1].name", is("Project 2")));

        verify(projectService, times(1)).getAllProjects();
    }

    @Test
    public void testGetProjectById() throws Exception {
        ProjectDto project = new ProjectDto(1L, "Project 1", "Description 1");

        when(projectService.getProjectById(1L)).thenReturn(project);

        mockMvc.perform(get("/api/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Project 1")))
                .andExpect(jsonPath("$.description", is("Description 1")));

        verify(projectService, times(1)).getProjectById(1L);
    }

    @Test
    public void testCreateProject() throws Exception {
        ProjectDto project = new ProjectDto(null, "New Project", "New Description");
        ProjectDto createdProject = new ProjectDto(1L, "New Project", "New Description");

        when(projectService.createProject(Mockito.any(ProjectDto.class))).thenReturn(createdProject);

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"name":"New Project","description":"New Description"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("New Project")))
                .andExpect(jsonPath("$.description", is("New Description")));

        verify(projectService, times(1)).createProject(Mockito.any(ProjectDto.class));
    }

    @Test
    public void testUpdateProject() throws Exception {
        ProjectDto updatedProject = new ProjectDto(1L, "Updated Project", "Updated Description");

        when(projectService.updateProject(eq(1L), Mockito.any(ProjectDto.class))).thenReturn(updatedProject);

        mockMvc.perform(put("/api/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"name":"Updated Project","description":"Updated Description"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Updated Project")))
                .andExpect(jsonPath("$.description", is("Updated Description")));

        verify(projectService, times(1)).updateProject(eq(1L), Mockito.any(ProjectDto.class));
    }

    @Test
    public void testDeleteProject() throws Exception {
        doNothing().when(projectService).deleteProject(1L);

        mockMvc.perform(delete("/api/projects/1"))
                .andExpect(status().isNoContent());

        verify(projectService, times(1)).deleteProject(1L);
    }
}
