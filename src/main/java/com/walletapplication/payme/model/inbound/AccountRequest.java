package com.walletapplication.payme.model.inbound;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
    private String name;
    private String email;
    private String password;
}
