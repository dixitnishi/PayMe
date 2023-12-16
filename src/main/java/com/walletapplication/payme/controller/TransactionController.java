package com.walletapplication.payme.controller;
import com.walletapplication.payme.model.entity.Transaction;
import com.walletapplication.payme.model.inbound.TransactionRequest;
import com.walletapplication.payme.model.outbound.TransactionResponse;
import com.walletapplication.payme.service.TransactionOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "http://localhost:3000/**")
public class TransactionController {

    @Autowired
    private TransactionOps transactionOps;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> sendMoney(@RequestBody TransactionRequest transactionRequest){
        return new ResponseEntity<>(transactionOps.sendMoney(transactionRequest), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<TransactionResponse> addMoney(@RequestBody TransactionRequest transactionRequest){
        return new ResponseEntity<>(transactionOps.addMoney(transactionRequest), HttpStatus.OK);
    }

    @GetMapping("/get/{accountNo}")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String accountNo){
        return new ResponseEntity<>(transactionOps.getTransactions(accountNo), HttpStatus.OK);
    }

}
