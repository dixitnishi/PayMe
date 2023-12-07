package com.walletapplication.payme.mapper;

import com.walletapplication.payme.model.entity.Transaction;
import com.walletapplication.payme.model.inbound.TransactionRequest;
import com.walletapplication.payme.model.outbound.TransactionResponse;
import org.mapstruct.Mapper;

@Mapper
public interface TransactionMapper {
    Transaction toTransaction(TransactionRequest transactionRequest);

    TransactionResponse toTransactionResponse(Transaction transaction);
}
