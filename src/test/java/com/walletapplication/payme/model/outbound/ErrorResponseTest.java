package com.walletapplication.payme.model.outbound;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorResponseTest {

    @Test
    void testBuilder() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        String message = "Error occurred";
        String code = "500";

        // Act
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timeStamp(timestamp)
                .message(message)
                .code(code)
                .build();

        // Assert
        assertEquals(timestamp, errorResponse.getTimeStamp());
        assertEquals(message, errorResponse.getMessage());
        assertEquals(code, errorResponse.getCode());
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        ErrorResponse errorResponse = new ErrorResponse();

        // Set values using setters
        LocalDateTime timestamp = LocalDateTime.now();
        String message = "Error occurred";
        String code = "500";

        errorResponse.setTimeStamp(timestamp);
        errorResponse.setMessage(message);
        errorResponse.setCode(code);

        // Assert using getters
        assertEquals(timestamp, errorResponse.getTimeStamp());
        assertEquals(message, errorResponse.getMessage());
        assertEquals(code, errorResponse.getCode());
    }
}
