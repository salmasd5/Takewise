package com.example.demo.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.JwtTokenProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        
        log.debug("Login attempt for email: {} with password: {}", email, password);

        // Check if user exists first
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            log.error("User not found with email: {}", email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "User not found"));
        }
        
        log.debug("Found user: {}, stored password hash: {}", user.getEmail(), user.getPassword());
        
        // Test password encoding
        String encodedPassword = passwordEncoder.encode(password);
        log.debug("Encoded input password: {}", encodedPassword);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            log.debug("Authentication successful: {}", authentication);

            String token = jwtTokenProvider.generateToken(email);
            log.debug("Token generated successfully: {}", token);
            
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            log.error("Authentication failed: ", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid credentials: " + e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> registerRequest) {
        String email = registerRequest.get("email");
        String password = registerRequest.get("password");
        String name = registerRequest.get("name");
        
        log.debug("Registration attempt for email: {}", email);

        // Validate input
        if (email == null || password == null || name == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email, password and name are required"));
        }

        // Check if user already exists
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already registered"));
        }

        try {
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setName(name);
            user.setUsername(email);

            userRepository.save(user);
            
            log.debug("User registered successfully");
            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        } catch (Exception e) {
            log.error("Registration failed: ", e);
            return ResponseEntity.badRequest().body(Map.of("error", "Registration failed: " + e.getMessage()));
        }
    }
}