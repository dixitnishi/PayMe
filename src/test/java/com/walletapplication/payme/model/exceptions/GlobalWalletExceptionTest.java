package com.walletapplication.payme.model.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GlobalWalletExceptionTest {

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
