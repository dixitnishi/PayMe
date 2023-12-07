package com.walletapplication.payme.service;

import com.walletapplication.payme.model.entity.Account;
import com.walletapplication.payme.model.exceptions.EntityNotFoundException;
import com.walletapplication.payme.model.inbound.AccountRequest;
import com.walletapplication.payme.model.outbound.AccountResponse;
import com.walletapplication.payme.repository.AccountRepo;
import com.walletapplication.payme.mapper.AccountMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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
            throw new EntityNotFoundException();
        } else {
            AccountResponse accountResponse = mapper.toAccountResponse(account.get());
            accountResponse.setMessage("Account deleted successfully");
            accountResponse.setAccountNo(account.get().getAccountNumber());
            accountResponse.setTimestamp(LocalDateTime.now());
            accountRepo.deleteById(accountNumber);
            return accountResponse;
        }

    }

    //    Update account details
    public AccountResponse updateAccount(AccountRequest accountRequest, String accountNumber) {
        Optional<Account> account = accountRepo.findById(accountNumber);
        if (account.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Account updatedAccount = mapper.toAccount(accountRequest);
        updatedAccount.setAccountNumber(account.get().getAccountNumber());
        accountRepo.save(updatedAccount);
        AccountResponse accountResponse = mapper.toAccountResponse(account.get());
        accountResponse.setAccountNo(account.get().getAccountNumber());
        accountResponse.setMessage("Account updated successfully");
        accountResponse.setTimestamp(LocalDateTime.now());
        return accountResponse;
    }

    //    Get Account details
    public AccountResponse getAccount(String accountNumber) {
        Optional<Account> account = accountRepo.findById(accountNumber);
        if (account.isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            AccountResponse accountResponse = mapper.toAccountResponse(account.get());
            accountResponse.setAccountNo(account.get().getAccountNumber());
            accountResponse.setMessage("Account reterived successfully");
            accountResponse.setTimestamp(LocalDateTime.now());
            return accountResponse;
        }
    }
}
