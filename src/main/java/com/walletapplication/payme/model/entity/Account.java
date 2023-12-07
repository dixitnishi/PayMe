package com.walletapplication.payme.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Data
@Builder
@Document(collection = "account")
public class Account {


    @Transient
    public static final String SEQUENCE_NAME = "account_sequence";

    @Id
    private String accountNumber;

    private String name;

    private String email;

    private String mobileNumber;

    private String password;

    private double balance;

}
