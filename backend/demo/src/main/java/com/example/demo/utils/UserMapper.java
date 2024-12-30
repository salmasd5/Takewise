package com.example.demo.utils;

import com.example.demo.dto.UserDto;
import com.example.demo.models.User;

public class UserMapper {
    private UserMapper() {
        // Private constructor to hide implicit public one
    }

    public static UserDto toDto(User user) {
        if (user == null) return null;
        return new UserDto(
            user.getId(),
            user.getName(),
            user.getUsername(),
            user.getEmail()
        );
    }
} 