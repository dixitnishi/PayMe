package com.walletapplication.payme.mapper;

import com.walletapplication.payme.model.entity.Account;
import com.walletapplication.payme.model.inbound.AccountRequest;
import com.walletapplication.payme.model.outbound.AccountResponse;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-09T09:27:31+0530",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 17.0.5 (Amazon.com Inc.)"
)
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account toAccount(AccountRequest accountRequest) {
        if ( accountRequest == null ) {
            return null;
        }

        Account.AccountBuilder account = Account.builder();

        account.name( accountRequest.getName() );
        account.email( accountRequest.getEmail() );
        account.mobileNumber( accountRequest.getMobileNumber() );
        account.password( accountRequest.getPassword() );

        return account.build();
    }

    @Override
    public AccountResponse toAccountResponse(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountResponse.AccountResponseBuilder accountResponse = AccountResponse.builder();

        accountResponse.name( account.getName() );
        accountResponse.email( account.getEmail() );
        accountResponse.mobileNumber( account.getMobileNumber() );

        return accountResponse.build();
    }
}
