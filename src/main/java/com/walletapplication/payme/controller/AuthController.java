package com.walletapplication.payme.controller;


import com.walletapplication.payme.model.inbound.AccountRequest;
import com.walletapplication.payme.model.inbound.LoginRequest;
import com.walletapplication.payme.model.outbound.AccountResponse;
import com.walletapplication.payme.model.outbound.LoginResponse;
import com.walletapplication.payme.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AccountResponse> signup(@RequestBody AccountRequest accountRequest) {
        return ResponseEntity.ok(authenticationService.signup(accountRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }
}
