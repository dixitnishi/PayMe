package com.walletapplication.payme.model.inbound;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    private String email;
    private String password;

}
