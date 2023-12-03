package com.walletapplication.payme.model.outbound;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private LocalDateTime timestamp;
    private String accountNo;
    private String message;
    private String name;
    private String email;
    private String mobileNumber;
    private String token;
}
