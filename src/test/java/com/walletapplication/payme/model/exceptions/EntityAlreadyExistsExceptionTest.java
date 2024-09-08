package com.walletapplication.payme.model.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static com.walletapplication.payme.model.exceptions.GlobalErrorCode.INVALID_EMAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EntityAlreadyExistsExceptionTest {

    @Test
    public void testDefaultConstructor() {
        // Arrange
        EntityAlreadyExistsException exception = new EntityAlreadyExistsException();

        // Act and Assert
        assertEquals("Requested Entity Already Exists", exception.getMessage());
        assertEquals(LocalDateTime.now().getDayOfYear(), exception.getTimeStamp().getDayOfYear());
        assertEquals(INVALID_EMAIL, exception.getCode());
    }

    @Test
    public void testParameterizedConstructor() {
        // Arrange
        String customMessage = "Custom Error Message";
        EntityAlreadyExistsException exception = new EntityAlreadyExistsException(customMessage);

        // Act and Assert
        assertEquals(customMessage, exception.getMessage());
        assertEquals(LocalDateTime.now().getDayOfYear(), exception.getTimeStamp().getDayOfYear());
        assertEquals(INVALID_EMAIL, exception.getCode());
    }
}
