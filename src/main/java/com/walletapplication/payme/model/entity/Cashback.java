package com.walletapplication.payme.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "cashback")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cashback {
    @Id
    private String transactionId;
    private LocalDateTime transactionTime;
    private String description;
    private double cashbackAmount;
    private String accountNumber;
}
