package com.walletapplication.payme.model.outbound;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

 class AccountResponseTest {

    @Test
    void testNoArgsConstructor() {
        // Arrange
        AccountResponse accountResponse = new AccountResponse();

        // Act & Assert
        assertEquals(null, accountResponse.getTimestamp());
        assertEquals(null, accountResponse.getAccountNo());
        assertEquals(null, accountResponse.getMessage());
        assertEquals(null, accountResponse.getName());
        assertEquals(null, accountResponse.getEmail());
        assertEquals(null, accountResponse.getBalance());
        assertEquals(null, accountResponse.getToken());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        String accountNo = "123456";
        String message = "Success";
        String name = "John Doe";
        String email = "john.doe@example.com";
        Double balance = 1000.0;
        String token = "token123";

        // Act
        AccountResponse accountResponse = new AccountResponse(timestamp, accountNo, message, name, email, balance, token);

        // Assert
        assertEquals(timestamp, accountResponse.getTimestamp());
        assertEquals(accountNo, accountResponse.getAccountNo());
        assertEquals(message, accountResponse.getMessage());
        assertEquals(name, accountResponse.getName());
        assertEquals(email, accountResponse.getEmail());
        assertEquals(balance, accountResponse.getBalance());
        assertEquals(token, accountResponse.getToken());
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        String accountNo = "789012";
        String message = "Failure";
        String name = "Jane Doe";
        String email = "jane.doe@example.com";
        Double balance = 500.0;
        String token = "token456";

        // Act
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setTimestamp(timestamp);
        accountResponse.setAccountNo(accountNo);
        accountResponse.setMessage(message);
        accountResponse.setName(name);
        accountResponse.setEmail(email);
        accountResponse.setBalance(balance);
        accountResponse.setToken(token);

        // Assert
        assertEquals(timestamp, accountResponse.getTimestamp());
        assertEquals(accountNo, accountResponse.getAccountNo());
        assertEquals(message, accountResponse.getMessage());
        assertEquals(name, accountResponse.getName());
        assertEquals(email, accountResponse.getEmail());
        assertEquals(balance, accountResponse.getBalance());
        assertEquals(token, accountResponse.getToken());
    }

    @Test
    void testBuilder() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        String accountNo = "345678";
        String message = "In Progress";
        String name = "Alice";
        String email = "alice@example.com";
        Double balance = 750.0;
        String token = "token789";

        // Act
        AccountResponse accountResponse = AccountResponse.builder()
                .timestamp(timestamp)
                .accountNo(accountNo)
                .message(message)
                .name(name)
                .email(email)
                .balance(balance)
                .token(token)
                .build();

        // Assert
        assertEquals(timestamp, accountResponse.getTimestamp());
        assertEquals(accountNo, accountResponse.getAccountNo());
        assertEquals(message, accountResponse.getMessage());
        assertEquals(name, accountResponse.getName());
        assertEquals(email, accountResponse.getEmail());
        assertEquals(balance, accountResponse.getBalance());
        assertEquals(token, accountResponse.getToken());
    }
}
