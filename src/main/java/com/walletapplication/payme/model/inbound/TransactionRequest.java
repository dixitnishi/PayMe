package com.walletapplication.payme.model.inbound;

import com.walletapplication.payme.model.enums.TRANSACTIONTYPE;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private TRANSACTIONTYPE transactionType;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private double amount;
}
