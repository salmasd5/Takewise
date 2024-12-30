package com.example.demo.testunitaire;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class TestControllerTest {

    @InjectMocks
    private TestController testController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProtectedEndpoint() {
        // Act
        ResponseEntity<?> response = testController.protectedEndpoint();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Map.of("message", "If you see this, you are authenticated!"), response.getBody());
    }
}
