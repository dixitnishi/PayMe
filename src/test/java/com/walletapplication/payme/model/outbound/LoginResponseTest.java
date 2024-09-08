package com.walletapplication.payme.model.outbound;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginResponseTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        LoginResponse loginResponse = new LoginResponse();

        // Set values using setters
        String token = "sampleToken";
        String accountId = "sampleAccountId";

        loginResponse.setToken(token);
        loginResponse.setAccountId(accountId);

        // Assert using getters
        assertEquals(token, loginResponse.getToken());
        assertEquals(accountId, loginResponse.getAccountId());
    }

    @Test
    void testBuilder() {
        // Arrange
        String token = "sampleToken";
        String accountId = "sampleAccountId";

        // Act
        LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .accountId(accountId)
                .build();

        // Assert using getters
        assertEquals(token, loginResponse.getToken());
        assertEquals(accountId, loginResponse.getAccountId());
    }
    @Test
    void testAllArgsConstructor() {
        // Arrange
        String token = "sampleToken";
        String accountId = "sampleAccountId";

        // Act
        LoginResponse loginResponse = new LoginResponse(token, accountId);

        // Assert using getters
        assertEquals(token, loginResponse.getToken());
        assertEquals(accountId, loginResponse.getAccountId());
    }
}
