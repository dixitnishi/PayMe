package com.walletapplication.payme.service;


import com.walletapplication.payme.model.entity.Account;
import com.walletapplication.payme.model.exceptions.EntityAlreadyExistsException;
import com.walletapplication.payme.model.exceptions.GlobalWalletException;
import com.walletapplication.payme.model.exceptions.InvalidDetailsEnteredException;
import com.walletapplication.payme.model.inbound.AccountRequest;
import com.walletapplication.payme.model.inbound.LoginRequest;
import com.walletapplication.payme.model.outbound.AccountResponse;
import com.walletapplication.payme.model.outbound.LoginResponse;
import com.walletapplication.payme.repository.AccountRepo;
import com.walletapplication.payme.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j(topic = "AuthenticationService")
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
                new UsernamePasswordAuthenticationToken(request.getEmail().trim(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        return LoginResponse
                .builder()
                .token(jwt)
                .accountId(accountRepo.findByEmail(request.getEmail()).get().getAccountNumber())
                .build();
    }


    public AccountResponse signup(AccountRequest request) {
        if(request.getEmail().isBlank()){
            log.error("Error while processing email, invalid email");
            throw new InvalidDetailsEnteredException("Please enter a valid email. Thanks!");
        }

        if (accountRepo.existsByEmail(request.getEmail().trim())) {
            log.error("Error while processing email {}", request.getEmail());
            throw new EntityAlreadyExistsException("Email already associated with existing wallet, please create wallet using different email. Thanks!");
        }


        var account = Account.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountNumber(String.valueOf(sequenceGenerator.generateSequence(Account.SEQUENCE_NAME)))
                .balance(0)
                .build();

        accountRepo.save(account);
        log.info("Wallet created with wallet id: {}",account.getAccountNumber());
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(account.getEmail().trim(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return AccountResponse.builder()
                .accountNo(account.getAccountNumber())
                .message("Wallet created successfully. Please try and login and continue using services...")
                .timestamp(LocalDateTime.now())
                .email(account.getEmail())
                .name(account.getName())
                .balance(account.getBalance())
                .token(jwt)
                .build();
    }

}
