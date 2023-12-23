package com.walletapplication.payme.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.walletapplication.payme.model.entity.Account;
import com.walletapplication.payme.model.exceptions.EntityAlreadyExistsException;
import com.walletapplication.payme.model.exceptions.InvalidDetailsEnteredException;
import com.walletapplication.payme.model.inbound.AccountRequest;
import com.walletapplication.payme.model.inbound.LoginRequest;
import com.walletapplication.payme.model.outbound.AccountResponse;
import com.walletapplication.payme.model.outbound.LoginResponse;
import com.walletapplication.payme.repository.AccountRepo;
import com.walletapplication.payme.security.JwtUtils;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthenticationService.class})
@ExtendWith(SpringExtension.class)
class AuthenticationServiceTest {
    @MockBean
    private AccountRepo accountRepo;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private SequenceGenerator sequenceGenerator;

    /**
     * Method under test: {@link AuthenticationService#login(LoginRequest)}
     */
    @Test
    void testLogin() throws AuthenticationException {
        // Arrange
        Account buildResult = Account.builder()
                .accountNumber("42")
                .balance(10.0d)
                .email("jane.doe@example.org")
                .name("Name")
                .password("iloveyou")
                .build();
        Optional<Account> ofResult = Optional.of(buildResult);
        when(accountRepo.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        when(jwtUtils.generateJwtToken(Mockito.<Authentication>any())).thenReturn("ABC123");
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

        // Act
        LoginResponse actualLoginResult = authenticationService.login(new LoginRequest("jane.doe@example.org", "iloveyou"));

        // Assert
        verify(accountRepo).findByEmail(Mockito.<String>any());
        verify(jwtUtils).generateJwtToken(Mockito.<Authentication>any());
        verify(authenticationManager).authenticate(Mockito.<Authentication>any());
        assertEquals("42", actualLoginResult.getAccountId());
        assertEquals("ABC123", actualLoginResult.getToken());
    }

    /**
     * Method under test: {@link AuthenticationService#login(LoginRequest)}
     */
    @Test
    void testLogin2() throws AuthenticationException {
        // Arrange
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenThrow(new InvalidDetailsEnteredException("An error occurred"));

        // Act and Assert
        assertThrows(InvalidDetailsEnteredException.class,
                () -> authenticationService.login(new LoginRequest("jane.doe@example.org", "iloveyou")));
        verify(authenticationManager).authenticate(Mockito.<Authentication>any());
    }

    /**
     * Method under test: {@link AuthenticationService#login(LoginRequest)}
     */
    @Test
    void testLogin3() {
        // Arrange
        LoginRequest request = mock(LoginRequest.class);
        when(request.getPassword()).thenThrow(new InvalidDetailsEnteredException("An error occurred"));
        when(request.getEmail()).thenReturn("jane.doe@example.org");

        // Act and Assert
        assertThrows(InvalidDetailsEnteredException.class, () -> authenticationService.login(request));
        verify(request).getEmail();
        verify(request).getPassword();
    }

    /**
     * Method under test: {@link AuthenticationService#signup(AccountRequest)}
     */
    @Test
    void testSignup() {
        // Arrange
        when(accountRepo.existsByEmail(Mockito.<String>any())).thenReturn(true);

        // Act and Assert
        assertThrows(EntityAlreadyExistsException.class,
                () -> authenticationService.signup(new AccountRequest("Name", "jane.doe@example.org", "iloveyou")));
        verify(accountRepo).existsByEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AuthenticationService#signup(AccountRequest)}
     */
    @Test
    void testSignup2() {
        // Arrange
        when(accountRepo.existsByEmail(Mockito.<String>any())).thenReturn(false);
        Account buildResult = Account.builder()
                .accountNumber("42")
                .balance(10.0d)
                .email("jane.doe@example.org")
                .name("Name")
                .password("iloveyou")
                .build();
        when(accountRepo.save(Mockito.<Account>any())).thenReturn(buildResult);
        when(sequenceGenerator.generateSequence(Mockito.<String>any())).thenReturn(1L);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        // Act
        AccountResponse actualSignupResult = authenticationService
                .signup(new AccountRequest("Name", "jane.doe@example.org", "iloveyou"));

        // Assert
        verify(accountRepo).existsByEmail(Mockito.<String>any());
        verify(sequenceGenerator).generateSequence(Mockito.<String>any());
        verify(accountRepo).save(Mockito.<Account>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
        assertEquals("1", actualSignupResult.getAccountNo());
        assertEquals("Name", actualSignupResult.getName());
        assertEquals("Wallet created successfully. Please try and login and continue using services...",
                actualSignupResult.getMessage());
        assertEquals("jane.doe@example.org", actualSignupResult.getEmail());
        assertEquals(0.0d, actualSignupResult.getBalance().doubleValue());
    }

    /**
     * Method under test: {@link AuthenticationService#signup(AccountRequest)}
     */
    @Test
    void testSignup3() {
        // Arrange
        when(accountRepo.existsByEmail(Mockito.<String>any())).thenReturn(false);
        when(passwordEncoder.encode(Mockito.<CharSequence>any()))
                .thenThrow(new InvalidDetailsEnteredException("An error occurred"));

        // Act and Assert
        assertThrows(InvalidDetailsEnteredException.class,
                () -> authenticationService.signup(new AccountRequest("Name", "jane.doe@example.org", "iloveyou")));
        verify(accountRepo).existsByEmail(Mockito.<String>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    }

    /**
     * Method under test: {@link AuthenticationService#signup(AccountRequest)}
     */
    @Test
    void testSignup4() {
        // Arrange, Act and Assert
        assertThrows(InvalidDetailsEnteredException.class,
                () -> authenticationService.signup(new AccountRequest("Name", "", "iloveyou")));
    }
}
