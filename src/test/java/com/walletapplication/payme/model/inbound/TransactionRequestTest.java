package com.walletapplication.payme.model.inbound;

import com.walletapplication.payme.model.enums.TRANSACTIONTYPE;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionRequestTest {

    @Test
    void testNoArgsConstructor() {
        // Arrange
        TransactionRequest transactionRequest = new TransactionRequest();

        // Act & Assert
        assertEquals(null, transactionRequest.getTransactionType());
        assertEquals(null, transactionRequest.getSenderAccountNumber());
        assertEquals(null, transactionRequest.getReceiverAccountNumber());
        assertEquals(0.0, transactionRequest.getAmount());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        TRANSACTIONTYPE transactionType = TRANSACTIONTYPE.DEBIT;
        String senderAccountNumber = "sender123";
        String receiverAccountNumber = "receiver456";
        double amount = 100.0;

        // Act
        TransactionRequest transactionRequest = new TransactionRequest(transactionType, senderAccountNumber, receiverAccountNumber, amount);

        // Assert
        assertEquals(transactionType, transactionRequest.getTransactionType());
        assertEquals(senderAccountNumber, transactionRequest.getSenderAccountNumber());
        assertEquals(receiverAccountNumber, transactionRequest.getReceiverAccountNumber());
        assertEquals(amount, transactionRequest.getAmount());
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        TRANSACTIONTYPE transactionType = TRANSACTIONTYPE.CREDIT;
        String senderAccountNumber = "sender789";
        String receiverAccountNumber = "receiver012";
        double amount = 50.0;

        // Act
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setTransactionType(transactionType);
        transactionRequest.setSenderAccountNumber(senderAccountNumber);
        transactionRequest.setReceiverAccountNumber(receiverAccountNumber);
        transactionRequest.setAmount(amount);

        // Assert
        assertEquals(transactionType, transactionRequest.getTransactionType());
        assertEquals(senderAccountNumber, transactionRequest.getSenderAccountNumber());
        assertEquals(receiverAccountNumber, transactionRequest.getReceiverAccountNumber());
        assertEquals(amount, transactionRequest.getAmount());
    }
}
