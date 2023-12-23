package com.walletapplication.payme.service;

import com.walletapplication.payme.model.entity.Account;
import com.walletapplication.payme.model.exceptions.EntityNotFoundException;
import com.walletapplication.payme.model.inbound.AccountRequest;
import com.walletapplication.payme.model.outbound.AccountResponse;
import com.walletapplication.payme.repository.AccountRepo;
import com.walletapplication.payme.mapper.AccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j(topic = "AccountOps")
@Service
public class AccountOps {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private SequenceGenerator sequenceGenerator;


    private final AccountMapper mapper = Mappers.getMapper(AccountMapper.class);

    //    Delete account
    public AccountResponse deleteAccount(String accountNumber) {

        Optional<Account> account = accountRepo.findById(accountNumber);


        if (account.isEmpty()) {
            log.error("Wallet does not exist: {}",accountNumber);
            throw new EntityNotFoundException("Wallet does not exists");
        } else {
            AccountResponse accountResponse = mapper.toAccountResponse(account.get());
            accountResponse.setMessage("Wallet deleted successfully");
            accountResponse.setAccountNo(account.get().getAccountNumber());
            accountResponse.setTimestamp(LocalDateTime.now());
            accountRepo.deleteById(accountNumber);
            log.info("Wallet deleted successfully, wallet id {}",accountNumber);
            return accountResponse;
        }
    }

    //    Update account details
    public AccountResponse updateAccount(AccountRequest accountRequest, String accountNumber) {
        Optional<Account> account = accountRepo.findById(accountNumber);
        if (account.isEmpty()) {
            log.error("Wallet does not exist: {}",accountNumber);
            throw new EntityNotFoundException("Wallet does not exists");
        }
        Account updatedAccount = mapper.toAccount(accountRequest);
        updatedAccount.setAccountNumber(account.get().getAccountNumber());
        accountRepo.save(updatedAccount);
        log.info("Wallet updated successfully, wallet id {}",accountNumber);
        AccountResponse accountResponse = mapper.toAccountResponse(account.get());
        accountResponse.setAccountNo(account.get().getAccountNumber());
        accountResponse.setMessage("Wallet updated successfully");
        accountResponse.setTimestamp(LocalDateTime.now());
        return accountResponse;
    }

    //    Get Account details
    public AccountResponse getAccount(String accountNumber) {
        Optional<Account> account = accountRepo.findById(accountNumber);
        if (account.isEmpty()) {
            log.error("Wallet does not exist: {}",accountNumber);
            throw new EntityNotFoundException("Wallet does not exists");
        } else {
            AccountResponse accountResponse = mapper.toAccountResponse(account.get());
            accountResponse.setAccountNo(account.get().getAccountNumber());
            accountResponse.setMessage("Wallet details reterived successfully");
            accountResponse.setTimestamp(LocalDateTime.now());
            log.info("Wallet reterived successfully, wallet id {}",accountNumber);
            return accountResponse;
        }
    }
}
