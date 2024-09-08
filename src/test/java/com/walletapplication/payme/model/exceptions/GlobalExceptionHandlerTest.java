package com.walletapplication.payme.model.exceptions;

import com.walletapplication.payme.model.outbound.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    public void testHandleGlobalException() {
        // Arrange
        GlobalWalletException globalWalletException = new GlobalWalletException("Error Message", LocalDateTime.now(),"Error Code");

        // Act
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleGlobalException(globalWalletException, Locale.getDefault());

        // Assert
        assertEquals(400, responseEntity.getStatusCodeValue());
        ErrorResponse errorResponse = responseEntity.getBody();
        assertEquals("Error Code", errorResponse.getCode());
        assertEquals("Error Message", errorResponse.getMessage());
    }
}
