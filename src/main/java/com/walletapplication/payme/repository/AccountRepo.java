package com.walletapplication.payme.repository;

import com.walletapplication.payme.model.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository

public interface AccountRepo extends MongoRepository<Account,String>{


    Optional<Account> findByEmail(String email);
    boolean existsByEmail(String email);

}
