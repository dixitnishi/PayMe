package com.walletapplication.payme.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.walletapplication.payme.mapper.TransactionMapper;
import com.walletapplication.payme.model.entity.Account;
import com.walletapplication.payme.model.entity.Transaction;
import com.walletapplication.payme.model.exceptions.EntityNotFoundException;
import com.walletapplication.payme.model.exceptions.GlobalErrorCode;
import com.walletapplication.payme.model.exceptions.GlobalWalletException;
import com.walletapplication.payme.model.inbound.TransactionRequest;
import com.walletapplication.payme.model.outbound.EmailDetails;
import com.walletapplication.payme.model.outbound.TransactionResponse;
import com.walletapplication.payme.repository.AccountRepo;
import com.walletapplication.payme.repository.TransactionRepo;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.walletapplication.payme.model.enums.TRANSACTIONTYPE.CREDIT;

@Service
public class TransactionOps {

    private final TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private EmailService emailService;

    @Transactional
    public TransactionResponse sendMoney(TransactionRequest transactionRequest) {
        Account senderAccount = validateAccountExists(transactionRequest.getSenderAccountNumber());
        Account receiverAccount = validateAccountExists(transactionRequest.getReceiverAccountNumber());
        validateAndTransferAmount(senderAccount, receiverAccount, transactionRequest.getAmount());
        saveTransactions(senderAccount,receiverAccount,transactionRequest);
        return TransactionResponse
                .builder()
                .status("SUCCESS")
                .description("Amount transferred from wallet " + senderAccount.getAccountNumber() +
                        " to " + receiverAccount.getAccountNumber() + " successfully")
                .timestamp(LocalDateTime.now())
                .build();
    }

    private void saveTransactions(Account senderAccount,Account receiverAccount, TransactionRequest transactionRequest) {
        String transactionId = generateTransactionId();
        Transaction transactionSenderPrespec = transactionMapper.toTransaction(transactionRequest);
        transactionSenderPrespec.setDescription("Amount transferred from wallet " + senderAccount.getAccountNumber() +
                " to " + receiverAccount.getAccountNumber() + " successfully");
        transactionSenderPrespec.setTransactionTime(LocalDateTime.now());
        transactionSenderPrespec.setTransactionId(transactionId);
        transactionRepo.save(transactionSenderPrespec);

        Transaction transactionReceiverPrespec = transactionMapper.toTransaction(transactionRequest);
        transactionReceiverPrespec.setDescription("Amount received from wallet " + senderAccount.getAccountNumber() +
                " to " + receiverAccount.getAccountNumber() + " successfully");
        transactionReceiverPrespec.setTransactionTime(LocalDateTime.now());
        transactionReceiverPrespec.setTransactionType(CREDIT);
        transactionReceiverPrespec.setTransactionId(transactionId);
        transactionRepo.save(transactionReceiverPrespec);
    }

    private void validateAndTransferAmount(Account senderAccount, Account receiverAccount, double transferAmount) {
        double intialSenderAccBal = senderAccount.getBalance();
        double intialReceiverAccBal = receiverAccount.getBalance();
        if (transferAmount > intialSenderAccBal) {
            throw new GlobalWalletException("Insufficient balance in senders account", LocalDateTime.now(), GlobalErrorCode.INSUFFICIENT_BALANCE);
        } else if (transferAmount > 0) {
            senderAccount.setBalance(intialSenderAccBal - transferAmount);
            receiverAccount.setBalance(intialReceiverAccBal + transferAmount);
        } else {
            throw new GlobalWalletException("Please enter a valid amount to transfer", LocalDateTime.now(), GlobalErrorCode.INVALID_AMOUNT);
        }
        accountRepo.save(senderAccount);
        accountRepo.save(receiverAccount);
    }

    private Account validateAccountExists(String accountNumber) {
        Optional<Account> account = accountRepo.findById(accountNumber);

        if(account.isPresent()){
            return account.get();
        }
        else{
            throw new EntityNotFoundException("Wallet with account number " + accountNumber + " does not exists");
        }
    }


    @Transactional
    public TransactionResponse addMoney(TransactionRequest transactionRequest) throws JsonProcessingException {
        Account account = validateAccountExists(transactionRequest.getSenderAccountNumber());
        double amountToBeAdded = transactionRequest.getAmount();
        double initialAmount = account.getBalance();
        if (amountToBeAdded > 0) {
            account.setBalance(initialAmount + amountToBeAdded);
        } else {
            throw new GlobalWalletException("Wallet balance insufficient for transaction", LocalDateTime.now(), GlobalErrorCode.INSUFFICIENT_BALANCE);
        }
        accountRepo.save(account);
        Transaction transaction = transactionMapper.toTransaction(transactionRequest);
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setDescription("Money added to wallet successfully");
        transaction.setReceiverAccountNumber("NA");
        transaction.setTransactionId(generateTransactionId());
        transactionRepo.save(transaction);
        TransactionResponse transactionResponse = transactionMapper.toTransactionResponse(transaction);
        transactionResponse.setStatus("SUCCESS");
        transactionResponse.setTimestamp(LocalDateTime.now());
        emailService.sendEmail(EmailDetails.builder()
                .receiverEmail(account.getEmail())
                .body(transaction.getDescription())
                .subject("Money Added To Wallet")
                .build());
        return transactionResponse;
    }

    public List<Transaction> getTransactions(String accountNo) {
//        return transactionRepo.findBySenderAccountNumberOrReceiverAccountNumber(accountNo, accountNo);
        return transactionRepo.findBySenderAccountNumber(accountNo);
    }

    public String generateTransactionId() {
        return UUID.randomUUID().toString();
    }
}
