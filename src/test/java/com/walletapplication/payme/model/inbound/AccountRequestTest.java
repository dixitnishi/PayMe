package com.walletapplication.payme.model.inbound;

import com.walletapplication.payme.model.inbound.AccountRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountRequestTest {

    @Test
    void testNoArgsConstructor() {
        // Arrange
        AccountRequest accountRequest = new AccountRequest();

        // Act & Assert
        assertEquals(null, accountRequest.getName());
        assertEquals(null, accountRequest.getEmail());
        assertEquals(null, accountRequest.getPassword());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String name = "John Doe";
        String email = "john.doe@example.com";
        String password = "securePassword";

        // Act
        AccountRequest accountRequest = new AccountRequest(name, email, password);

        // Assert
        assertEquals(name, accountRequest.getName());
        assertEquals(email, accountRequest.getEmail());
        assertEquals(password, accountRequest.getPassword());
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        String name = "Jane Smith";
        String email = "jane.smith@example.com";
        String password = "strongPassword";

        // Act
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setName(name);
        accountRequest.setEmail(email);
        accountRequest.setPassword(password);

        // Assert
        assertEquals(name, accountRequest.getName());
        assertEquals(email, accountRequest.getEmail());
        assertEquals(password, accountRequest.getPassword());
    }

    @Test
    void testBuilder() {
        // Arrange
        String name = "Alice";
        String email = "alice@example.com";
        String password = "alicePassword";

        // Act
        AccountRequest accountRequest = AccountRequest.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();

        // Assert
        assertEquals(name, accountRequest.getName());
        assertEquals(email, accountRequest.getEmail());
        assertEquals(password, accountRequest.getPassword());
    }
}
