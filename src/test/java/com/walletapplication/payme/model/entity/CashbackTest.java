package com.walletapplication.payme.model.entity;

import com.walletapplication.payme.model.entity.Cashback;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CashbackTest {

    @Test
    void testCreateCashback() {
        // Arrange
        String transactionId = "123";
        LocalDateTime transactionTime = LocalDateTime.now();
        String description = "Cashback for purchase";
        double cashbackAmount = 10.0;
        String accountNumber = "ABC123";

        // Act
        Cashback cashback = new Cashback(transactionId, transactionTime, description, cashbackAmount, accountNumber);

        // Assert
        assertEquals(transactionId, cashback.getTransactionId());
        assertEquals(transactionTime, cashback.getTransactionTime());
        assertEquals(description, cashback.getDescription());
        assertEquals(cashbackAmount, cashback.getCashbackAmount());
        assertEquals(accountNumber, cashback.getAccountNumber());
    }

    @Test
    void testCashbackBuilder() {
        // Arrange
        String transactionId = "123";
        LocalDateTime transactionTime = LocalDateTime.now();
        String description = "Cashback for purchase";
        double cashbackAmount = 10.0;
        String accountNumber = "ABC123";

        // Act
        Cashback cashback = Cashback.builder()
                .transactionId(transactionId)
                .transactionTime(transactionTime)
                .description(description)
                .cashbackAmount(cashbackAmount)
                .accountNumber(accountNumber)
                .build();

        // Assert
        assertEquals(transactionId, cashback.getTransactionId());
        assertEquals(transactionTime, cashback.getTransactionTime());
        assertEquals(description, cashback.getDescription());
        assertEquals(cashbackAmount, cashback.getCashbackAmount());
        assertEquals(accountNumber, cashback.getAccountNumber());
    }
}
