package com.walletapplication.payme.model.outbound;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionResponseTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        TransactionResponse transactionResponse = new TransactionResponse();

        // Set values using setters
        LocalDateTime timestamp = LocalDateTime.now();
        String description = "Transaction completed";
        String status = "Success";
        double cashbackReceived = 10.0;

        transactionResponse.setTimestamp(timestamp);
        transactionResponse.setDescription(description);
        transactionResponse.setStatus(status);
        transactionResponse.setCashbackReceived(cashbackReceived);

        // Assert using getters
        assertEquals(timestamp, transactionResponse.getTimestamp());
        assertEquals(description, transactionResponse.getDescription());
        assertEquals(status, transactionResponse.getStatus());
        assertEquals(cashbackReceived, transactionResponse.getCashbackReceived());
    }

    @Test
    void testBuilder() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        String description = "Transaction completed";
        String status = "Success";
        double cashbackReceived = 10.0;

        // Act
        TransactionResponse transactionResponse = TransactionResponse.builder()
                .timestamp(timestamp)
                .description(description)
                .status(status)
                .cashbackReceived(cashbackReceived)
                .build();

        // Assert using getters
        assertEquals(timestamp, transactionResponse.getTimestamp());
        assertEquals(description, transactionResponse.getDescription());
        assertEquals(status, transactionResponse.getStatus());
        assertEquals(cashbackReceived, transactionResponse.getCashbackReceived());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        String description = "Transaction completed";
        String status = "Success";
        double cashbackReceived = 10.0;

        // Act
        TransactionResponse transactionResponse = new TransactionResponse(timestamp, description, status, cashbackReceived);

        // Assert using getters
        assertEquals(timestamp, transactionResponse.getTimestamp());
        assertEquals(description, transactionResponse.getDescription());
        assertEquals(status, transactionResponse.getStatus());
        assertEquals(cashbackReceived, transactionResponse.getCashbackReceived());
    }
}
