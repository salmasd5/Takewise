package com.example.demo.utils;

import java.util.stream.Collectors;

import com.example.demo.dto.TeamDto;
import com.example.demo.models.Team;

public class TeamMapper {
    private TeamMapper() {}

    public static TeamDto toDto(Team team) {
        if (team == null) return null;
        return new TeamDto(
            team.getId(),
            team.getName(),
            team.getDescription(),
            team.getMembers().stream()
                .map(user -> user.getId())
                .collect(Collectors.toSet())
        );
    }
} 