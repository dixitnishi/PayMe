package com.walletapplication.payme.model.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GlobalWalletExceptionTest {
    @Test
    void setMessage_shouldSetMessage() {
        // Arrange
        GlobalWalletException exception = new GlobalWalletException();

        // Act
        exception.setMessage("New Message");

        // Assert
        assertEquals("New Message", exception.getMessage());
    }

    @Test
    void getTimeStamp_shouldReturnTimeStamp() {
        // Arrange
        LocalDateTime timeStamp = LocalDateTime.now();
        GlobalWalletException exception = new GlobalWalletException("Test Message", timeStamp, "CODE");

        // Act & Assert
        assertEquals(timeStamp, exception.getTimeStamp());
    }

    @Test
    void setTimeStamp_shouldSetTimeStamp() {
        // Arrange
        GlobalWalletException exception = new GlobalWalletException();

        // Act
        LocalDateTime timeStamp = LocalDateTime.now();
        exception.setTimeStamp(timeStamp);

        // Assert
        assertEquals(timeStamp, exception.getTimeStamp());
    }

    @Test
    void getCode_shouldReturnCode() {
        // Arrange
        GlobalWalletException exception = new GlobalWalletException("Test Message", LocalDateTime.now(), "CODE");

        // Act & Assert
        assertEquals("CODE", exception.getCode());
    }

    @Test
    void setCode_shouldSetCode() {
        // Arrange
        GlobalWalletException exception = new GlobalWalletException();

        // Act
        exception.setCode("NEW_CODE");

        // Assert
        assertEquals("NEW_CODE", exception.getCode());
    }

    @Test
    void constructorWithMessage() {
        String errorMessage = "Test Error Message";
        GlobalWalletException exception = new GlobalWalletException(errorMessage);
        assertEquals(exception.getTimeStamp(), exception.getTimeStamp());
        assertEquals(exception.getCode(), exception.getCode());
    }

    @Test
    void fullConstructor() {
        String errorMessage = "Test Error Message";
        LocalDateTime timestamp = LocalDateTime.now();
        String code = "TEST_CODE";
        GlobalWalletException exception = new GlobalWalletException(errorMessage, timestamp, code);

        assertEquals(errorMessage, exception.getMessage());
        assertEquals(timestamp, exception.getTimeStamp());
        assertEquals(code, exception.getCode());
    }
}
