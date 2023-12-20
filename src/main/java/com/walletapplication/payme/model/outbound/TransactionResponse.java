package com.walletapplication.payme.model.outbound;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private LocalDateTime timestamp;
    private String description;
    private String status;
    private double cashbackReceived;
}
