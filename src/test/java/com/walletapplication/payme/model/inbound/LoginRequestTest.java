package com.walletapplication.payme.model.inbound;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginRequestTest {

    @Test
    void testNoArgsConstructor() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();

        // Act & Assert
        assertEquals(null, loginRequest.getEmail());
        assertEquals(null, loginRequest.getPassword());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String email = "john.doe@example.com";
        String password = "securePassword";

        // Act
        LoginRequest loginRequest = new LoginRequest(email, password);

        // Assert
        assertEquals(email, loginRequest.getEmail());
        assertEquals(password, loginRequest.getPassword());
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        String email = "jane.smith@example.com";
        String password = "strongPassword";

        // Act
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        // Assert
        assertEquals(email, loginRequest.getEmail());
        assertEquals(password, loginRequest.getPassword());
    }

    @Test
    void testBuilder() {
        // Arrange
        String email = "alice@example.com";
        String password = "alicePassword";

        // Act
        LoginRequest loginRequest = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        // Assert
        assertEquals(email, loginRequest.getEmail());
        assertEquals(password, loginRequest.getPassword());
    }
}
