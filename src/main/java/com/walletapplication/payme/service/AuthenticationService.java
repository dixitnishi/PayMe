package com.walletapplication.payme.service;


import com.walletapplication.payme.model.entity.Account;
import com.walletapplication.payme.model.exceptions.EntityAlreadyExistsException;
import com.walletapplication.payme.model.inbound.AccountRequest;
import com.walletapplication.payme.model.inbound.LoginRequest;
import com.walletapplication.payme.model.outbound.AccountResponse;
import com.walletapplication.payme.model.outbound.LoginResponse;
import com.walletapplication.payme.repository.AccountRepo;
import com.walletapplication.payme.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final JwtUtils jwtUtils;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private SequenceGenerator sequenceGenerator;

    public LoginResponse login(LoginRequest request) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getAccountNo().trim(), request.getPassword().trim()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        return LoginResponse
                .builder()
                .token(jwt)
                .build();
    }


    public AccountResponse signup(AccountRequest request) {
        if (accountRepo.existsByEmail(request.getEmail().trim()))
            throw new EntityAlreadyExistsException();

        var account = Account.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .mobileNumber(request.getMobileNumber())
                .accountNumber(String.valueOf(sequenceGenerator.generateSequence(Account.SEQUENCE_NAME)))
                .balance(0)
                .build();

        accountRepo.save(account);
        return AccountResponse.builder()
                .accountNo(account.getAccountNumber())
                .message("Wallet registered successfully")
                .timestamp(LocalDateTime.now())
                .email(account.getEmail())
                .mobileNumber(account.getMobileNumber())
                .name(account.getName())
                .build();
    }

}
