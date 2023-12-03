package com.walletapplication.payme.controller;
import com.walletapplication.payme.model.exceptions.CommonException;
import com.walletapplication.payme.model.inbound.TransactionRequest;
import com.walletapplication.payme.service.TransactionOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionOps transactionOps;

    @PostMapping("/transfer")
    public ResponseEntity<?> sendMoney(@RequestBody TransactionRequest transactionRequest) throws CommonException {
        return new ResponseEntity<>(transactionOps.sendMoney(transactionRequest), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMoney(@RequestBody TransactionRequest transactionRequest) throws CommonException {
        return new ResponseEntity<>(transactionOps.addMoney(transactionRequest), HttpStatus.OK);
    }

    @GetMapping("/get/{accountNo}")
    public ResponseEntity<?> getTransactions(@PathVariable String accountNo){
        return new ResponseEntity<>(transactionOps.getTransactions(accountNo), HttpStatus.OK);
    }

}
