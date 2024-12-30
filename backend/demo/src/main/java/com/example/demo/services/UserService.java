package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.UserMapper;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create or update a user
    public UserDto saveUser(UserDto userDto) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        User savedUser = userRepository.save(user);
        return UserMapper.toDto(savedUser);
    }

    // Get a user by its ID
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toDto(user);
    }

    // Get a user by username
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Get a user by email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Delete a user by its ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Check if a user exists by username
    public boolean userExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    // Check if a user exists by email
    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserDto createUser(UserDto userDto) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword()); // In a real app, you should hash the password

        User savedUser = userRepository.save(user);
        return UserMapper.toDto(savedUser);
    }
}
