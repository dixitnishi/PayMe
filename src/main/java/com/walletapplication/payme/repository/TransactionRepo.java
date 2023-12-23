package com.walletapplication.payme.repository;

import com.walletapplication.payme.model.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepo extends MongoRepository<Transaction,String> {

    List<Transaction> findByAssociatedAccount(String accountNumber);
}
