package com.walletapplication.payme.model.entity;

import com.walletapplication.payme.model.enums.TRANSACTIONTYPE;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        Transaction transaction = new Transaction();
        String transactionId = "123";
        LocalDateTime transactionTime = LocalDateTime.now();
        String description = "Test Transaction";
        double amount = 100.0;
        TRANSACTIONTYPE transactionType = TRANSACTIONTYPE.CREDIT;
        String senderAccountNumber = "sender123";
        String receiverAccountNumber = "receiver456";
        String associatedAccount = "associated789";

        // Act
        transaction.setTransactionId(transactionId);
        transaction.setTransactionTime(transactionTime);
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);
        transaction.setSenderAccountNumber(senderAccountNumber);
        transaction.setReceiverAccountNumber(receiverAccountNumber);
        transaction.setAssociatedAccount(associatedAccount);

        // Assert
        assertEquals(transactionId, transaction.getTransactionId());
        assertEquals(transactionTime, transaction.getTransactionTime());
        assertEquals(description, transaction.getDescription());
        assertEquals(amount, transaction.getAmount());
        assertEquals(transactionType, transaction.getTransactionType());
        assertEquals(senderAccountNumber, transaction.getSenderAccountNumber());
        assertEquals(receiverAccountNumber, transaction.getReceiverAccountNumber());
        assertEquals(associatedAccount, transaction.getAssociatedAccount());
    }
}
