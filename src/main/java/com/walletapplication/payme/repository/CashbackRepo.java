package com.walletapplication.payme.repository;

import com.walletapplication.payme.model.entity.Cashback;
import com.walletapplication.payme.model.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CashbackRepo extends MongoRepository<Cashback,String> {
    List<Cashback> findByAccountNumber(String senderAccountNumber);
}
