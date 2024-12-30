package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TeamDto;
import com.example.demo.services.TeamService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        List<TeamDto> teams = teamService.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable Long id) {
        TeamDto team = teamService.getTeamById(id);
        return ResponseEntity.ok(team);
    }

    @PostMapping
    public ResponseEntity<TeamDto> createTeam(@Valid @RequestBody TeamDto teamDto) {
        TeamDto createdTeam = teamService.createTeam(teamDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeam);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable Long id, 
                                            @Valid @RequestBody TeamDto teamDto) {
        TeamDto updatedTeam = teamService.updateTeam(id, teamDto);
        return ResponseEntity.ok(updatedTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}
