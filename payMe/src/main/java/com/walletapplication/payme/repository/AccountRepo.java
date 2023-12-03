package com.walletapplication.payme.repository;

import com.walletapplication.payme.model.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface AccountRepo extends MongoRepository<Account,String>{

}
