package com.walletapplication.payme.repository;

import com.walletapplication.payme.model.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface AccountRepo extends MongoRepository<Account,String>{

    boolean existsByEmail(String email);

}
