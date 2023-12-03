package com.walletapplication.payme.mapper;


import com.walletapplication.payme.model.entity.Account;
import com.walletapplication.payme.model.inbound.AccountRequest;
import com.walletapplication.payme.model.outbound.AccountResponse;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {

    Account toAccount(AccountRequest accountRequest);

    AccountResponse toAccountResponse(Account account);
}
