package com.walletapplication.payme.security;

import com.walletapplication.payme.model.entity.Account;
import com.walletapplication.payme.repository.AccountRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private Account account;

    @Mock
    private AccountRepo accountRepo;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername_shouldReturnUserDetails() {
        // Arrange
        String email = "test@example.com";
        Account mockAccount = Account.builder()
                .accountNumber("42")
                .balance(10.0d)
                .email("test@example.com")
                .name("Name")
                .password("iloveyou")
                .build();
        when(accountRepo.findByEmail(email)).thenReturn(Optional.of(mockAccount));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Assert
        assertEquals(mockAccount.getEmail(), userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        String nonExistentEmail = "nonexistent@example.com";
        when(accountRepo.findByEmail(nonExistentEmail)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(nonExistentEmail));
    }
}
