package com.walletapplication.payme.controller;
import com.walletapplication.payme.model.inbound.AccountRequest;
import com.walletapplication.payme.model.inbound.LoginRequest;
import com.walletapplication.payme.model.outbound.AccountResponse;
import com.walletapplication.payme.model.outbound.LoginResponse;
import com.walletapplication.payme.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationService authenticationServiceMock;

    @InjectMocks
    private AuthController authController;

    @Test
    public void testSignup() {
        AccountRequest accountRequest = new AccountRequest("John", "john@example.com", "password");
        AccountResponse mockAccountResponse = new AccountResponse(
                LocalDateTime.now(), "123", "Details fetched successfully", "John", "john@example.com", 0.00, "mockedToken");
        when(authenticationServiceMock.signup(accountRequest)).thenReturn(mockAccountResponse);
        ResponseEntity<AccountResponse> responseEntity = authController.signup(accountRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockAccountResponse, responseEntity.getBody());
        verify(authenticationServiceMock, times(1)).signup(accountRequest);
    }

    @Test
    public void testLogin() {
        LoginRequest loginRequest = new LoginRequest("john@example.com", "password");
        LoginResponse mockLoginResponse = new LoginResponse("mockedToken","123");
        when(authenticationServiceMock.login(loginRequest)).thenReturn(mockLoginResponse);
        ResponseEntity<LoginResponse> responseEntity = authController.login(loginRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockLoginResponse, responseEntity.getBody());
        verify(authenticationServiceMock, times(1)).login(loginRequest);
    }
}
