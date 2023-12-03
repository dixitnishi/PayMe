package com.walletapplication.payme.service;

import com.walletapplication.payme.model.entity.Account;
import com.walletapplication.payme.model.inbound.AccountRequest;
import com.walletapplication.payme.model.inbound.LoginRequest;
import com.walletapplication.payme.model.outbound.AccountResponse;
import com.walletapplication.payme.model.outbound.LoginResponse;
import com.walletapplication.payme.repository.AccountRepo;
import com.walletapplication.payme.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepo accountRepo;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    public AccountResponse register(AccountRequest accountRequest) {
        var account = Account.builder()
                .email(accountRequest.getEmail())
                .name(accountRequest.getName())
                .password(passwordEncoder.encode(accountRequest.getPassword()))
                .mobileNumber(accountRequest.getMobileNumber())
                .accountNumber(String.valueOf(sequenceGenerator.generateSequence(Account.SEQUENCE_NAME)))
                .balance(0)
                .build();
        accountRepo.save(account);

        var jwtToken = jwtService.generateToken(account);
        return AccountResponse.builder()
                .accountNo(account.getAccountNumber())
                .token(jwtToken)
                .message("Wallet registered successfully")
                .timestamp(LocalDateTime.now())
                .email(account.getEmail())
                .mobileNumber(account.getMobileNumber())
                .build();
    }


    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getAccountNo(),
                        loginRequest.getPassword()
                )
        );
        var account  = accountRepo.findById(loginRequest.getAccountNo())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        var jwtToken = jwtService.generateToken(account);
        return LoginResponse.builder()
                .token(jwtToken)
                .build();

    }
}

