package com.walletapplication.payme.model.inbound;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
    private String name;
    private String email;
    private String mobileNumber;
    private String password;
}
