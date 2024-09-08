package com.walletapplication.payme.security;

import com.walletapplication.payme.model.entity.Account;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class UserDetailsImplTest {

    @Test
    void build_shouldCreateUserDetailsImplFromAccount() {
        // Arrange
        Account mockAccount = Account.builder()
                .accountNumber("123456789")
                .balance(100.0)
                .email("test@example.com")
                .name("Name")
                .password("password")
                .build();
        // Act
        UserDetailsImpl userDetails = UserDetailsImpl.build(mockAccount);

        // Assert
        assertEquals("123456789", userDetails.getAccountNumber());
        assertEquals("Name", userDetails.getName());
        assertEquals("test@example.com", userDetails.getEmail());
        assertEquals("password", userDetails.getPassword());
        assertEquals(100.0, userDetails.getBalance());
    }

    @Test
    void getUsername_shouldReturnEmail() {
        // Arrange
        UserDetailsImpl userDetails = new UserDetailsImpl("123456789", "John Doe", "john@example.com", "password", 100.0);

        // Act & Assert
        assertEquals("john@example.com", userDetails.getUsername());
    }

    @Test
    void isAccountNonExpired_shouldAlwaysReturnTrue() {
        // Arrange
        UserDetailsImpl userDetails = new UserDetailsImpl("123456789", "John Doe", "john@example.com", "password", 100.0);

        // Act & Assert
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked_shouldAlwaysReturnTrue() {
        // Arrange
        UserDetailsImpl userDetails = new UserDetailsImpl("123456789", "John Doe", "john@example.com", "password", 100.0);

        // Act & Assert
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired_shouldAlwaysReturnTrue() {
        // Arrange
        UserDetailsImpl userDetails = new UserDetailsImpl("123456789", "John Doe", "john@example.com", "password", 100.0);

        // Act & Assert
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void isEnabled_shouldAlwaysReturnTrue() {
        // Arrange
        UserDetailsImpl userDetails = new UserDetailsImpl("123456789", "John Doe", "john@example.com", "password", 100.0);

        // Act & Assert
        assertTrue(userDetails.isEnabled());
    }
}
