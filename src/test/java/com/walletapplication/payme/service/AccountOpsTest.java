package com.walletapplication.payme.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.walletapplication.payme.model.entity.Account;
import com.walletapplication.payme.model.exceptions.EntityNotFoundException;
import com.walletapplication.payme.model.inbound.AccountRequest;
import com.walletapplication.payme.model.outbound.AccountResponse;
import com.walletapplication.payme.repository.AccountRepo;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AccountOps.class})
@ExtendWith(SpringExtension.class)
class AccountOpsTest {
    @Autowired
    private AccountOps accountOps;

    @MockBean
    private AccountRepo accountRepo;

    @MockBean
    private SequenceGenerator sequenceGenerator;

    /**
     * Method under test: {@link AccountOps#deleteAccount(String)}
     */
    @Test
    void testDeleteAccount() {
        // Arrange
        doNothing().when(accountRepo).deleteById(Mockito.<String>any());
        Account buildResult = Account.builder()
                .accountNumber("42")
                .balance(10.0d)
                .email("jane.doe@example.org")
                .name("Name")
                .password("iloveyou")
                .build();
        Optional<Account> ofResult = Optional.of(buildResult);
        when(accountRepo.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        AccountResponse actualDeleteAccountResult = accountOps.deleteAccount("42");

        // Assert
        verify(accountRepo).deleteById(Mockito.<String>any());
        verify(accountRepo).findById(Mockito.<String>any());
        assertEquals("42", actualDeleteAccountResult.getAccountNo());
        assertEquals("Name", actualDeleteAccountResult.getName());
        assertEquals("Wallet deleted successfully", actualDeleteAccountResult.getMessage());
        assertEquals("jane.doe@example.org", actualDeleteAccountResult.getEmail());
        assertEquals(10.0d, actualDeleteAccountResult.getBalance().doubleValue());
    }

    /**
     * Method under test: {@link AccountOps#deleteAccount(String)}
     */
    @Test
    void testDeleteAccount2() {
        // Arrange
        doThrow(new EntityNotFoundException()).when(accountRepo).deleteById(Mockito.<String>any());
        Account buildResult = Account.builder()
                .accountNumber("42")
                .balance(10.0d)
                .email("jane.doe@example.org")
                .name("Name")
                .password("iloveyou")
                .build();
        Optional<Account> ofResult = Optional.of(buildResult);
        when(accountRepo.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> accountOps.deleteAccount("42"));
        verify(accountRepo).deleteById(Mockito.<String>any());
        verify(accountRepo).findById(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AccountOps#deleteAccount(String)}
     */
    @Test
    void testDeleteAccount3() {
        // Arrange
        Optional<Account> emptyResult = Optional.empty();
        when(accountRepo.findById(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> accountOps.deleteAccount("42"));
        verify(accountRepo).findById(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AccountOps#updateAccount(AccountRequest, String)}
     */
    @Test
    void testUpdateAccount() {
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

        // Act
        AccountResponse actualUpdateAccountResult = accountOps
                .updateAccount(new AccountRequest("Name", "jane.doe@example.org", "iloveyou"), "42");

        // Assert
        verify(accountRepo).findById(Mockito.<String>any());
        verify(accountRepo).save(Mockito.<Account>any());
        assertEquals("42", actualUpdateAccountResult.getAccountNo());
        assertEquals("Name", actualUpdateAccountResult.getName());
        assertEquals("Wallet updated successfully", actualUpdateAccountResult.getMessage());
        assertEquals("jane.doe@example.org", actualUpdateAccountResult.getEmail());
        assertEquals(10.0d, actualUpdateAccountResult.getBalance().doubleValue());
    }

    /**
     * Method under test: {@link AccountOps#updateAccount(AccountRequest, String)}
     */
    @Test
    void testUpdateAccount2() {
        // Arrange
        when(accountRepo.save(Mockito.<Account>any())).thenThrow(new EntityNotFoundException());
        Account buildResult = Account.builder()
                .accountNumber("42")
                .balance(10.0d)
                .email("jane.doe@example.org")
                .name("Name")
                .password("iloveyou")
                .build();
        Optional<Account> ofResult = Optional.of(buildResult);
        when(accountRepo.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(EntityNotFoundException.class,
                () -> accountOps.updateAccount(new AccountRequest("Name", "jane.doe@example.org", "iloveyou"), "42"));
        verify(accountRepo).findById(Mockito.<String>any());
        verify(accountRepo).save(Mockito.<Account>any());
    }

    /**
     * Method under test: {@link AccountOps#updateAccount(AccountRequest, String)}
     */
    @Test
    void testUpdateAccount3() {
        // Arrange
        Optional<Account> emptyResult = Optional.empty();
        when(accountRepo.findById(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(EntityNotFoundException.class,
                () -> accountOps.updateAccount(new AccountRequest("Name", "jane.doe@example.org", "iloveyou"), "42"));
        verify(accountRepo).findById(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AccountOps#updateAccount(AccountRequest, String)}
     */
    @Test
    void testUpdateAccount4() {
        // Arrange
        Account buildResult = Account.builder()
                .accountNumber("42")
                .balance(10.0d)
                .email("jane.doe@example.org")
                .name("Name")
                .password("iloveyou")
                .build();
        Optional<Account> ofResult = Optional.of(buildResult);
        when(accountRepo.findById(Mockito.<String>any())).thenReturn(ofResult);
        AccountRequest accountRequest = mock(AccountRequest.class);
        when(accountRequest.getName()).thenThrow(new EntityNotFoundException());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> accountOps.updateAccount(accountRequest, "42"));
        verify(accountRequest).getName();
        verify(accountRepo).findById(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AccountOps#getAccount(String)}
     */
    @Test
    void testGetAccount() {
        // Arrange
        Account buildResult = Account.builder()
                .accountNumber("42")
                .balance(10.0d)
                .email("jane.doe@example.org")
                .name("Name")
                .password("iloveyou")
                .build();
        Optional<Account> ofResult = Optional.of(buildResult);
        when(accountRepo.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        AccountResponse actualAccount = accountOps.getAccount("42");

        // Assert
        verify(accountRepo).findById(Mockito.<String>any());
        assertEquals("42", actualAccount.getAccountNo());
        assertEquals("Name", actualAccount.getName());
        assertEquals("Wallet details reterived successfully", actualAccount.getMessage());
        assertEquals("jane.doe@example.org", actualAccount.getEmail());
        assertEquals(10.0d, actualAccount.getBalance().doubleValue());
    }

    /**
     * Method under test: {@link AccountOps#getAccount(String)}
     */
    @Test
    void testGetAccount2() {
        // Arrange
        Optional<Account> emptyResult = Optional.empty();
        when(accountRepo.findById(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> accountOps.getAccount("42"));
        verify(accountRepo).findById(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AccountOps#getAccount(String)}
     */
    @Test
    void testGetAccount3() {
        // Arrange
        when(accountRepo.findById(Mockito.<String>any())).thenThrow(new EntityNotFoundException());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> accountOps.getAccount("42"));
        verify(accountRepo).findById(Mockito.<String>any());
    }
}
