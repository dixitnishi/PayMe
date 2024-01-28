package com.walletapplication.payme.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.walletapplication.payme.model.entity.Account;
import com.walletapplication.payme.model.entity.Cashback;
import com.walletapplication.payme.model.entity.Transaction;
import com.walletapplication.payme.model.enums.TRANSACTIONTYPE;
import com.walletapplication.payme.model.exceptions.EntityNotFoundException;
import com.walletapplication.payme.model.exceptions.GlobalWalletException;
import com.walletapplication.payme.model.exceptions.InvalidDetailsEnteredException;
import com.walletapplication.payme.model.inbound.TransactionRequest;
import com.walletapplication.payme.model.outbound.EmailDetails;
import com.walletapplication.payme.model.outbound.TransactionResponse;
import com.walletapplication.payme.repository.AccountRepo;
import com.walletapplication.payme.repository.CashbackRepo;
import com.walletapplication.payme.repository.TransactionRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TransactionOps.class})
@ExtendWith(SpringExtension.class)
class TransactionOpsTest {
    @MockBean
    private AccountRepo accountRepo;

    @MockBean
    private CashbackRepo cashbackRepo;

    @MockBean
    private EmailService emailService;

    @Autowired
    private TransactionOps transactionOps;

    @MockBean
    private TransactionRepo transactionRepo;

    @Test
    void testSendMoney() {
        // Arrange, Act and Assert
        assertThrows(InvalidDetailsEnteredException.class,
                () -> transactionOps.sendMoney(new TransactionRequest(TRANSACTIONTYPE.CREDIT, "42", "42", 10.0d)));
    }

    @Test
    void testSendMoney2() {
        // Arrange
        Account buildResult = Account.builder()
                .accountNumber("42")
                .balance(10.0d)
                .email("jane.doe@example.org")
                .name("Name")
                .password("iloveyou")
                .build();
        when(accountRepo.save(Mockito.<Account>any())).thenReturn(buildResult);
        Account buildResult2 = Account.builder()
                .accountNumber("42")
                .balance(10.0d)
                .email("jane.doe@example.org")
                .name("Name")
                .password("iloveyou")
                .build();
        Optional<Account> ofResult = Optional.of(buildResult2);
        when(accountRepo.findById(Mockito.<String>any())).thenReturn(ofResult);

        Cashback cashback = new Cashback();
        cashback.setAccountNumber("42");
        cashback.setCashbackAmount(10.0d);
        cashback.setDescription("The characteristics of someone or something");
        cashback.setTransactionId("42");
        cashback.setTransactionTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(cashbackRepo.save(Mockito.<Cashback>any())).thenReturn(cashback);
        doNothing().when(emailService).sendEmail(Mockito.<EmailDetails>any());

        Transaction transaction = new Transaction();
        transaction.setAmount(10.0d);
        transaction.setAssociatedAccount("3");
        transaction.setDescription("The characteristics of someone or something");
        transaction.setReceiverAccountNumber("42");
        transaction.setSenderAccountNumber("42");
        transaction.setTransactionId("42");
        transaction.setTransactionTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        transaction.setTransactionType(TRANSACTIONTYPE.CREDIT);
        when(transactionRepo.save(Mockito.<Transaction>any())).thenReturn(transaction);

        // Act
        TransactionResponse actualSendMoneyResult = transactionOps
                .sendMoney(new TransactionRequest(TRANSACTIONTYPE.CREDIT, "3", "42", 10.0d));

        // Assert
        verify(emailService, atLeast(1)).sendEmail(Mockito.<EmailDetails>any());
        verify(accountRepo, atLeast(1)).findById(Mockito.<String>any());
        verify(cashbackRepo).save(Mockito.<Cashback>any());
        verify(accountRepo, atLeast(1)).save(Mockito.<Account>any());
        verify(transactionRepo, atLeast(1)).save(Mockito.<Transaction>any());
        assertEquals("SUCCESS", actualSendMoneyResult.getStatus());
    }

//    @Test
//    void testSendMoney3() {
//        // Arrange
//        Account buildResult = Account.builder()
//                .accountNumber("42")
//                .balance(10.0d)
//                .email("jane.doe@example.org")
//                .name("Name")
//                .password("iloveyou")
//                .build();
//        when(accountRepo.save(Mockito.<Account>any())).thenReturn(buildResult);
//        Account buildResult2 = Account.builder()
//                .accountNumber("42")
//                .balance(10.0d)
//                .email("jane.doe@example.org")
//                .name("Name")
//                .password("iloveyou")
//                .build();
//        Optional<Account> ofResult = Optional.of(buildResult2);
//        when(accountRepo.findById(Mockito.<String>any())).thenReturn(ofResult);
//
//        Cashback cashback = new Cashback();
//        cashback.setAccountNumber("42");
//        cashback.setCashbackAmount(10.0d);
//        cashback.setDescription("The characteristics of someone or something");
//        cashback.setTransactionId("42");
//        cashback.setTransactionTime(LocalDate.of(1970, 1, 1).atStartOfDay());
//        when(cashbackRepo.save(Mockito.<Cashback>any())).thenReturn(cashback);
//        when(transactionRepo.save(Mockito.<Transaction>any()))
//                .thenThrow(new InvalidDetailsEnteredException("An error occurred"));
//
//        // Act and Assert
//        assertThrows(InvalidDetailsEnteredException.class,
//                () -> transactionOps.sendMoney(new TransactionRequest(TRANSACTIONTYPE.CREDIT, "3", "42", 10.0d)));
//        verify(accountRepo, atLeast(1)).findById(Mockito.<String>any());
//        verify(transactionRepo).save(Mockito.<Transaction>any());
//        verify(accountRepo, atLeast(1)).save(Mockito.<Account>any());
//    }

    @Test
    void testSendMoney4() {
        // Arrange
        Account buildResult = Account.builder()
                .accountNumber("42")
                .balance(0.5d)
                .email("jane.doe@example.org")
                .name("Name")
                .password("iloveyou")
                .build();
        Optional<Account> ofResult = Optional.of(buildResult);
        when(accountRepo.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(GlobalWalletException.class,
                () -> transactionOps.sendMoney(new TransactionRequest(TRANSACTIONTYPE.CREDIT, "3", "42", 10.0d)));
        verify(accountRepo, atLeast(1)).findById(Mockito.<String>any());
    }

    @Test
    void testSendMoney5() {
        // Arrange
        Optional<Account> emptyResult = Optional.empty();
        when(accountRepo.findById(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(EntityNotFoundException.class,
                () -> transactionOps.sendMoney(new TransactionRequest(TRANSACTIONTYPE.CREDIT, "3", "42", 10.0d)));
        verify(accountRepo).findById(Mockito.<String>any());
    }

    @Test
    void testAddMoney() {
        // Arrange
        Account buildResult = Account.builder()
                .accountNumber("42")
                .balance(10.0d)
                .email("jane.doe@example.org")
                .name("Name")
                .password("iloveyou")
                .build();
        when(accountRepo.save(Mockito.<Account>any())).thenReturn(buildResult);
        Account buildResult2 = Account.builder()
                .accountNumber("42")
                .balance(10.0d)
                .email("jane.doe@example.org")
                .name("Name")
                .password("iloveyou")
                .build();
        Optional<Account> ofResult = Optional.of(buildResult2);
        when(accountRepo.findById(Mockito.<String>any())).thenReturn(ofResult);

        Cashback cashback = new Cashback();
        cashback.setAccountNumber("42");
        cashback.setCashbackAmount(10.0d);
        cashback.setDescription("The characteristics of someone or something");
        cashback.setTransactionId("42");
        cashback.setTransactionTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(cashbackRepo.save(Mockito.<Cashback>any())).thenReturn(cashback);
        doNothing().when(emailService).sendEmail(Mockito.<EmailDetails>any());

        Transaction transaction = new Transaction();
        transaction.setAmount(10.0d);
        transaction.setAssociatedAccount("3");
        transaction.setDescription("The characteristics of someone or something");
        transaction.setReceiverAccountNumber("42");
        transaction.setSenderAccountNumber("42");
        transaction.setTransactionId("42");
        transaction.setTransactionTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        transaction.setTransactionType(TRANSACTIONTYPE.CREDIT);
        when(transactionRepo.save(Mockito.<Transaction>any())).thenReturn(transaction);

        // Act
        TransactionResponse actualAddMoneyResult = transactionOps
                .addMoney(new TransactionRequest(TRANSACTIONTYPE.CREDIT, "42", "42", 10.0d));

        // Assert
        verify(emailService).sendEmail(Mockito.<EmailDetails>any());
        verify(accountRepo).findById(Mockito.<String>any());
        verify(accountRepo).save(Mockito.<Account>any());
        verify(cashbackRepo).save(Mockito.<Cashback>any());
        verify(transactionRepo).save(Mockito.<Transaction>any());
        assertEquals("SUCCESS", actualAddMoneyResult.getStatus());
    }

    @Test
    void testAddMoney2() {
        // Arrange
        Account buildResult = Account.builder()
                .accountNumber("42")
                .balance(10.0d)
                .email("jane.doe@example.org")
                .name("Name")
                .password("iloveyou")
                .build();
        when(accountRepo.save(Mockito.<Account>any())).thenReturn(buildResult);
        Account buildResult2 = Account.builder()
                .accountNumber("42")
                .balance(10.0d)
                .email("jane.doe@example.org")
                .name("Name")
                .password("iloveyou")
                .build();
        Optional<Account> ofResult = Optional.of(buildResult2);
        when(accountRepo.findById(Mockito.<String>any())).thenReturn(ofResult);

        Cashback cashback = new Cashback();
        cashback.setAccountNumber("42");
        cashback.setCashbackAmount(10.0d);
        cashback.setDescription("The characteristics of someone or something");
        cashback.setTransactionId("42");
        cashback.setTransactionTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(cashbackRepo.save(Mockito.<Cashback>any())).thenReturn(cashback);
        when(transactionRepo.save(Mockito.<Transaction>any()))
                .thenThrow(new InvalidDetailsEnteredException("An error occurred"));

        // Act and Assert
        assertThrows(InvalidDetailsEnteredException.class,
                () -> transactionOps.addMoney(new TransactionRequest(TRANSACTIONTYPE.CREDIT, "42", "42", 10.0d)));
        verify(accountRepo).findById(Mockito.<String>any());
        verify(accountRepo).save(Mockito.<Account>any());
        verify(transactionRepo).save(Mockito.<Transaction>any());
    }


    @Test
    void testAddMoney3() {
        // Arrange
        Optional<Account> emptyResult = Optional.empty();
        when(accountRepo.findById(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(EntityNotFoundException.class,
                () -> transactionOps.addMoney(new TransactionRequest(TRANSACTIONTYPE.CREDIT, "42", "42", 10.0d)));
        verify(accountRepo).findById(Mockito.<String>any());
    }


    @Test
    void testAddMoney4() {
        // Arrange, Act and Assert
        assertThrows(GlobalWalletException.class,
                () -> transactionOps.addMoney(new TransactionRequest(TRANSACTIONTYPE.CREDIT, "42", "42", 0.0d)));
    }


    @Test
    void testGetTransactions() {
        // Arrange
        ArrayList<Transaction> transactionList = new ArrayList<>();
        when(transactionRepo.findByAssociatedAccount(Mockito.<String>any())).thenReturn(transactionList);

        // Act
        List<Transaction> actualTransactions = transactionOps.getTransactions("3");

        // Assert
        verify(transactionRepo).findByAssociatedAccount(Mockito.<String>any());
        assertTrue(actualTransactions.isEmpty());
        assertSame(transactionList, actualTransactions);
    }


    @Test
    void testGetTransactions2() {
        // Arrange
        when(transactionRepo.findByAssociatedAccount(Mockito.<String>any()))
                .thenThrow(new InvalidDetailsEnteredException("An error occurred"));

        // Act and Assert
        assertThrows(InvalidDetailsEnteredException.class, () -> transactionOps.getTransactions("3"));
        verify(transactionRepo).findByAssociatedAccount(Mockito.<String>any());
    }


    @Test
    void testGetCashbacks() {
        // Arrange
        ArrayList<Cashback> cashbackList = new ArrayList<>();
        when(cashbackRepo.findByAccountNumber(Mockito.<String>any())).thenReturn(cashbackList);

        // Act
        List<Cashback> actualCashbacks = transactionOps.getCashbacks("3");

        // Assert
        verify(cashbackRepo).findByAccountNumber(Mockito.<String>any());
        assertTrue(actualCashbacks.isEmpty());
        assertSame(cashbackList, actualCashbacks);
    }


    @Test
    void testGetCashbacks2() {
        // Arrange
        when(cashbackRepo.findByAccountNumber(Mockito.<String>any()))
                .thenThrow(new InvalidDetailsEnteredException("An error occurred"));

        // Act and Assert
        assertThrows(InvalidDetailsEnteredException.class, () -> transactionOps.getCashbacks("3"));
        verify(cashbackRepo).findByAccountNumber(Mockito.<String>any());
    }
}
