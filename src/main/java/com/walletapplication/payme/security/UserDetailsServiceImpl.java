package com.walletapplication.payme.security;

import com.walletapplication.payme.model.entity.Account;
import com.walletapplication.payme.repository.AccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

import static com.walletapplication.payme.model.exceptions.GlobalErrorCode.ERROR_WALLET_NOT_FOUND;


@Service
@RequiredArgsConstructor

public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private AccountRepo accountRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Account account  = accountRepo.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(MessageFormat.format(ERROR_WALLET_NOT_FOUND,email)));
        return UserDetailsImpl.build(account);
    }
}
