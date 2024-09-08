package com.walletapplication.payme.controller;

import com.walletapplication.payme.model.outbound.AccountResponse;
import com.walletapplication.payme.service.AccountOps;
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
public class AccountControllerTest {

    @Mock
    private AccountOps accountOpsMock;

    @InjectMocks
    private AccountController accountController;

    @Test
    public void testGet() {
        String accountNo = "123";
        AccountResponse mockAccountResponse = new AccountResponse(
                LocalDateTime.now(), "123", "Details fetched successfully", "Nishi", "nishi@gmail.com", 0.00, "mockedToken");

        // Mock the service behavior
        when(accountOpsMock.getAccount(anyString())).thenReturn(mockAccountResponse);

        // Act
        ResponseEntity<?> responseEntity = accountController.get(accountNo);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockAccountResponse, responseEntity.getBody());

        // Verify that the method was called with the correct parameter
        verify(accountOpsMock, times(1)).getAccount(accountNo);
    }
}
