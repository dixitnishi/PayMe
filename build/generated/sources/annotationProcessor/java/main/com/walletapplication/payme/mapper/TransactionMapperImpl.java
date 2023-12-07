package com.walletapplication.payme.mapper;

import com.walletapplication.payme.model.entity.Transaction;
import com.walletapplication.payme.model.inbound.TransactionRequest;
import com.walletapplication.payme.model.outbound.TransactionResponse;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-07T13:59:41+0530",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 17.0.5 (Amazon.com Inc.)"
)
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction toTransaction(TransactionRequest transactionRequest) {
        if ( transactionRequest == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setAmount( transactionRequest.getAmount() );
        transaction.setTransactionType( transactionRequest.getTransactionType() );
        transaction.setSenderAccountNumber( transactionRequest.getSenderAccountNumber() );
        transaction.setReceiverAccountNumber( transactionRequest.getReceiverAccountNumber() );

        return transaction;
    }

    @Override
    public TransactionResponse toTransactionResponse(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionResponse transactionResponse = new TransactionResponse();

        transactionResponse.setDescription( transaction.getDescription() );

        return transactionResponse;
    }
}
