package com.walletapplication.payme.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.walletapplication.payme.model.entity.Cashback;
import com.walletapplication.payme.model.entity.Transaction;
import com.walletapplication.payme.model.enums.TRANSACTIONTYPE;
import com.walletapplication.payme.model.inbound.TransactionRequest;
import com.walletapplication.payme.model.outbound.TransactionResponse;
import com.walletapplication.payme.service.TransactionOps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TransactionControllerTest {

    @Mock
    private TransactionOps transactionOpsMock;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    public void testSendMoney() {
        TransactionRequest transactionRequest = new TransactionRequest(TRANSACTIONTYPE.DEBIT, "456", "123", 50.0);
        TransactionResponse mockTransactionResponse = new TransactionResponse(LocalDateTime.now(),"Transaction was success","SUCCESS",1.0);

        // Mock the service behavior
        when(transactionOpsMock.sendMoney(transactionRequest)).thenReturn(mockTransactionResponse);

        // Act
        ResponseEntity<TransactionResponse> responseEntity = transactionController.sendMoney(transactionRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockTransactionResponse, responseEntity.getBody());

        // Verify that the method was called with the correct parameter
        verify(transactionOpsMock, times(1)).sendMoney(transactionRequest);
    }

    @Test
    public void testAddMoney() throws JsonProcessingException {
        TransactionRequest transactionRequest = new TransactionRequest(TRANSACTIONTYPE.DEBIT, "456", "", 50.0);
        TransactionResponse mockTransactionResponse = new TransactionResponse(LocalDateTime.now(),"Transaction was success","SUCCESS",1.0);

        // Mock the service behavior
        when(transactionOpsMock.addMoney(transactionRequest)).thenReturn(mockTransactionResponse);

        // Act
        ResponseEntity<TransactionResponse> responseEntity = transactionController.addMoney(transactionRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockTransactionResponse, responseEntity.getBody());

        // Verify that the method was called with the correct parameter
        verify(transactionOpsMock, times(1)).addMoney(transactionRequest);
    }

    @Test
    public void testGetTransactions() {
        String accountNo = "123";
        List<Transaction> mockTransactions = new ArrayList<>();
        mockTransactions.add(new Transaction("1234",LocalDateTime.now(),"Transaction success",10.0,TRANSACTIONTYPE.CREDIT,"123","456","123"));

        // Mock the service behavior
        when(transactionOpsMock.getTransactions(accountNo)).thenReturn(mockTransactions);

        // Act
        ResponseEntity<List<Transaction>> responseEntity = transactionController.getTransactions(accountNo);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockTransactions, responseEntity.getBody());

        // Verify that the method was called with the correct parameter
        verify(transactionOpsMock, times(1)).getTransactions(accountNo);
    }

    @Test
    public void testGetCashbacks() {
        String accountNo = "123";
        List<Cashback> mockCashbacks = Collections.singletonList(new Cashback("123",LocalDateTime.now(),"Cashback received",1.00,"123"));

        // Mock the service behavior
        when(transactionOpsMock.getCashbacks(accountNo)).thenReturn(mockCashbacks);

        // Act
        ResponseEntity<List<Cashback>> responseEntity = transactionController.getCashbacks(accountNo);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockCashbacks, responseEntity.getBody());

        // Verify that the method was called with the correct parameter
        verify(transactionOpsMock, times(1)).getCashbacks(accountNo);
    }
}
