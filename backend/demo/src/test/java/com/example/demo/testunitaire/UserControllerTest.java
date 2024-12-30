package com.example.demo.testunitaire;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.JwtTokenProvider;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        String token = "jwt-token";

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mock(Authentication.class));
        when(jwtTokenProvider.generateToken(email)).thenReturn(token);

        Map<String, String> loginRequest = Map.of("email", email, "password", password);

        // Act
        ResponseEntity<?> response = authController.login(loginRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Map.of("token", token), response.getBody());
    }

    @Test
    void testLoginUserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        String password = "password123";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Map<String, String> loginRequest = Map.of("email", email, "password", password);

        // Act
        ResponseEntity<?> response = authController.login(loginRequest);

        // Assert
        assertEquals(401, response.getStatusCodeValue());
        assertEquals(Map.of("error", "User not found"), response.getBody());
    }

    @Test
    void testLoginInvalidCredentials() {
        // Arrange
        String email = "test@example.com";
        String password = "wrongpassword";

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException("Invalid credentials"));

        Map<String, String> loginRequest = Map.of("email", email, "password", password);

        // Act
        ResponseEntity<?> response = authController.login(loginRequest);

        // Assert
        assertEquals(401, response.getStatusCodeValue());
        assertEquals(Map.of("error", "Invalid credentials: Invalid credentials"), response.getBody());
    }

    @Test
    void testRegisterSuccess() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        String name = "Test User";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn("encoded-password");

        Map<String, String> registerRequest = Map.of("email", email, "password", password, "name", name);

        // Act
        ResponseEntity<?> response = authController.register(registerRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Map.of("message", "User registered successfully"), response.getBody());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterEmailAlreadyExists() {
        // Arrange
        String email = "test@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        Map<String, String> registerRequest = Map.of("email", email, "password", "password123", "name", "Test User");

        // Act
        ResponseEntity<?> response = authController.register(registerRequest);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(Map.of("error", "Email already registered"), response.getBody());
    }

    @Test
    void testRegisterMissingFields() {
        // Arrange
        Map<String, String> registerRequest = Map.of("email", "test@example.com", "password", "password123"); // Missing name

        // Act
        ResponseEntity<?> response = authController.register(registerRequest);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(Map.of("error", "Email, password and name are required"), response.getBody());
    }
}
