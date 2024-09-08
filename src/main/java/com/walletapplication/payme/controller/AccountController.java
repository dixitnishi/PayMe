package com.walletapplication.payme.controller;


import com.walletapplication.payme.model.inbound.AccountRequest;
import com.walletapplication.payme.service.AccountOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountOps accountOperations;

    @GetMapping("/get/{accountNo}")
    public ResponseEntity<?> get(@PathVariable String accountNo){
        return new ResponseEntity<>(accountOperations.getAccount(accountNo),HttpStatus.OK);
    }
}
