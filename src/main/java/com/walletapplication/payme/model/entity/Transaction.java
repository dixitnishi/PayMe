package com.walletapplication.payme.model.entity;

import com.walletapplication.payme.model.enums.TRANSACTIONTYPE;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "transactions")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private String transactionId;
    private LocalDateTime transactionTime;
    private String description;
    private double amount;
    private TRANSACTIONTYPE transactionType;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private String associatedAccount;
}
