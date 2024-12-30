package com.example.demo.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TeamDto;
import com.example.demo.models.Team;
import com.example.demo.models.User;
import com.example.demo.repositories.TeamRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.TeamMapper;

@Service
public class TeamService {
    private static final String TEAM_NOT_FOUND = "Team not found";
    
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public List<TeamDto> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(TeamMapper::toDto)
                .toList();
    }

    public TeamDto getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(TEAM_NOT_FOUND));
        return TeamMapper.toDto(team);
    }

    public TeamDto createTeam(TeamDto teamDto) {
        Team team = new Team();
        team.setName(teamDto.getName());
        team.setDescription(teamDto.getDescription());
        
        if (teamDto.getMemberIds() != null) {
            Set<User> members = teamDto.getMemberIds().stream()
                    .map(userId -> userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found: " + userId)))
                    .collect(Collectors.toSet());
            team.setMembers(members);
        }

        Team savedTeam = teamRepository.save(team);
        return TeamMapper.toDto(savedTeam);
    }

    public TeamDto updateTeam(Long id, TeamDto teamDto) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(TEAM_NOT_FOUND));

        team.setName(teamDto.getName());
        team.setDescription(teamDto.getDescription());
        
        if (teamDto.getMemberIds() != null) {
            Set<User> members = teamDto.getMemberIds().stream()
                    .map(userId -> userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found: " + userId)))
                    .collect(Collectors.toSet());
            team.setMembers(members);
        }

        Team updatedTeam = teamRepository.save(team);
        return TeamMapper.toDto(updatedTeam);
    }

    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new RuntimeException(TEAM_NOT_FOUND);
        }
        teamRepository.deleteById(id);
    }
}
