package com.walletapplication.payme.service;
import com.walletapplication.payme.mapper.TransactionMapper;
import com.walletapplication.payme.model.entity.Account;
import com.walletapplication.payme.model.entity.Transaction;
import com.walletapplication.payme.model.exceptions.EntityNotFoundException;
import com.walletapplication.payme.model.exceptions.GlobalErrorCode;
import com.walletapplication.payme.model.exceptions.GlobalWalletException;
import com.walletapplication.payme.model.inbound.TransactionRequest;
import com.walletapplication.payme.model.outbound.TransactionResponse;
import com.walletapplication.payme.repository.AccountRepo;
import com.walletapplication.payme.repository.TransactionRepo;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.walletapplication.payme.model.enums.TRANSACTIONTYPE.CREDIT;

@Service
public class TransactionOps {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private AccountRepo accountRepo;

    private final TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);
//    Transfer amount
//    Add money to self account

    @Transactional
    public TransactionResponse sendMoney(TransactionRequest transactionRequest){
        if(accountRepo.findById(transactionRequest.getSenderAccountNumber()).isPresent()
            && accountRepo.findById(transactionRequest.getReceiverAccountNumber()).isPresent()){
            Account senderAccount  = accountRepo.findById(transactionRequest.getSenderAccountNumber()).get();
            Account receiverAccount =  accountRepo.findById(transactionRequest.getReceiverAccountNumber()).get();

//            main logic for transferring the amount
            double intialSenderAccBal = senderAccount.getBalance();
            double intialReceiverAccBal = receiverAccount.getBalance();
            double transferAmount = transactionRequest.getAmount();
            String transactionId = generateTransactionId();


            if(transferAmount > intialSenderAccBal){
                throw new GlobalWalletException("Insufficient balance in senders account", LocalDateTime.now(),GlobalErrorCode.INSUFFICIENT_BALANCE);            }
            else if(transferAmount>0){
                senderAccount.setBalance(intialSenderAccBal-transferAmount);
                receiverAccount.setBalance(intialReceiverAccBal+transferAmount);
            }else{
                throw new GlobalWalletException("Please enter a valid amount to transfer", LocalDateTime.now(),GlobalErrorCode.INVALID_AMOUNT);
            }
//            saving the updated balances
            accountRepo.save(senderAccount);
            accountRepo.save(receiverAccount);

//            logic for saving the transaction in the transaction collection - dual transaction entries both prespectives
            Transaction transactionSenderPrespec = transactionMapper.toTransaction(transactionRequest);
            transactionSenderPrespec.setDescription("Amount transferred from wallet "+senderAccount.getAccountNumber()+
                    " to "+receiverAccount.getAccountNumber()+" successfully");
            transactionSenderPrespec.setTransactionTime(LocalDateTime.now());
            transactionSenderPrespec.setTransactionId(transactionId);
            transactionRepo.save(transactionSenderPrespec);

            Transaction transactionReceiverPrespec = transactionMapper.toTransaction(transactionRequest);
            transactionReceiverPrespec.setDescription("Amount received from wallet "+senderAccount.getAccountNumber()+
                    " to "+receiverAccount.getAccountNumber()+" successfully");
            transactionReceiverPrespec.setTransactionTime(LocalDateTime.now());
            transactionReceiverPrespec.setTransactionType(CREDIT);
            transactionReceiverPrespec.setTransactionId(transactionId);
            transactionRepo.save(transactionReceiverPrespec);

//            Creating th transaction response
            TransactionResponse transactionResponse  = transactionMapper.toTransactionResponse(transactionSenderPrespec);
            transactionResponse.setStatus("SUCCESS");
            transactionResponse.setTimestamp(LocalDateTime.now());
            return transactionResponse;
        }
        else if(accountRepo.findById(transactionRequest.getSenderAccountNumber()).isEmpty()
        && accountRepo.findById(transactionRequest.getReceiverAccountNumber()).isPresent()){
            throw new EntityNotFoundException("Sender account "+transactionRequest.getSenderAccountNumber()+" does not exist, Please enter a valid sender's account number");
        }
        else if((accountRepo.findById(transactionRequest.getSenderAccountNumber()).isPresent())
                && accountRepo.findById(transactionRequest.getReceiverAccountNumber()).isEmpty()){
            throw new EntityNotFoundException("Receiver account "+transactionRequest.getReceiverAccountNumber()+" does not exist, Please enter a valid receiver's account number");
        }
        else{
            throw new EntityNotFoundException("Sender and receiver account "+transactionRequest.getSenderAccountNumber()+" & "+transactionRequest.getReceiverAccountNumber()+" does not exist, Please use valid accounts");
        }
    }


    @Transactional
    public TransactionResponse addMoney(TransactionRequest transactionRequest){
//        Check whether the account exists
        if(accountRepo.findById(transactionRequest.getSenderAccountNumber()).isPresent()){
            Account account = accountRepo.findById(transactionRequest.getSenderAccountNumber()).get();
            double amountToBeAdded = transactionRequest.getAmount();
            double initialAmount = account.getBalance();
            if(amountToBeAdded>0){
                account.setBalance(initialAmount+amountToBeAdded);
            }
            else{
                throw new GlobalWalletException("Wallet balance insufficient for transaction",LocalDateTime.now(), GlobalErrorCode.INSUFFICIENT_BALANCE);
            }

//          Account is updated, need to make sure no new entry is added when we add the balance
            accountRepo.save(account);

//             Creating a new transaction entry in the transaction repo

            Transaction transaction = transactionMapper.toTransaction(transactionRequest);
            transaction.setTransactionTime(LocalDateTime.now());
            transaction.setDescription("Money added to wallet successfully");
            transaction.setReceiverAccountNumber("NA");
            transaction.setTransactionId(generateTransactionId());
            transactionRepo.save(transaction);
            TransactionResponse transactionResponse = transactionMapper.toTransactionResponse(transaction);
            transactionResponse.setStatus("SUCCESS");
            transactionResponse.setTimestamp(LocalDateTime.now());
            return transactionResponse;
        }else{
            throw new EntityNotFoundException();
        }
    }

    public List<Transaction> getTransactions(String accountNo) {
        return transactionRepo.findBySenderAccountNumberOrReceiverAccountNumber(accountNo,accountNo);
    }

    public String generateTransactionId(){
        return UUID.randomUUID().toString();
    }

//     To do add the function to fetch all the transactions for the account number requested
//     optional functions to be added fetch the most recent transaction



}
