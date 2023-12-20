package com.walletapplication.payme.service;


import com.walletapplication.payme.mapper.TransactionMapper;
import com.walletapplication.payme.model.entity.Account;
import com.walletapplication.payme.model.entity.Cashback;
import com.walletapplication.payme.model.entity.Transaction;
import com.walletapplication.payme.model.exceptions.EntityNotFoundException;
import com.walletapplication.payme.model.exceptions.GlobalErrorCode;
import com.walletapplication.payme.model.exceptions.GlobalWalletException;
import com.walletapplication.payme.model.inbound.TransactionRequest;
import com.walletapplication.payme.model.outbound.EmailDetails;
import com.walletapplication.payme.model.outbound.TransactionResponse;
import com.walletapplication.payme.repository.AccountRepo;
import com.walletapplication.payme.repository.CashbackRepo;
import com.walletapplication.payme.repository.TransactionRepo;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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

    @Autowired
    private CashbackRepo cashbackRepo;

    private Random random = new Random();

    @Transactional
    public TransactionResponse sendMoney(TransactionRequest transactionRequest) {
        Account senderAccount = validateAccountExists(transactionRequest.getSenderAccountNumber());
        Account receiverAccount = validateAccountExists(transactionRequest.getReceiverAccountNumber());
        double cashback = calculateCashback(transactionRequest.getAmount());
        String transactionId = generateTransactionId();
        validateAndTransferAmount(senderAccount, receiverAccount, transactionRequest.getAmount(), cashback);
        saveTransactions(senderAccount, receiverAccount, transactionRequest, transactionId);
        saveCashback(senderAccount, cashback, transactionId, false);

        //      Sending the async email sending task
        emailService.sendEmail(EmailDetails.builder()
                .receiverEmail(receiverAccount.getEmail())
                .body("Amount RS." + transactionRequest.getAmount() + " received from " + senderAccount.getAccountNumber())
                .subject("Amount Credited")
                .build());
        emailService.sendEmail(EmailDetails.builder()
                .receiverEmail(senderAccount.getEmail())
                .body("Amount RS." + transactionRequest.getAmount() + " send to " + receiverAccount.getAccountNumber()+" and you received a cashback of RS."+cashback+" which is added to your wallet balance.")
                .subject("Amount debited")
                .build());

        return TransactionResponse
                .builder()
                .status("SUCCESS")
                .description("Amount transferred from wallet " + receiverAccount.getAccountNumber())
                .cashbackReceived(cashback)
                .timestamp(LocalDateTime.now())
                .build();
    }

    private void saveCashback(Account account, double cashback, String transactionId, boolean isAddMoneyTrue) {
        String descriptionCashback = isAddMoneyTrue ? "Cashback received on adding money to wallet" : "Cashback received on transferring amount";

        Cashback cashbackObject = Cashback
                .builder()
                .cashbackAmount(cashback)
                .transactionId(transactionId)
                .transactionTime(LocalDateTime.now())
                .accountNumber(account.getAccountNumber())
                .description(descriptionCashback)
                .build();
        cashbackRepo.save(cashbackObject);
    }

    private void saveTransactions(Account senderAccount, Account receiverAccount, TransactionRequest transactionRequest, String transactionId) {
        Transaction transactionSenderPrespec = transactionMapper.toTransaction(transactionRequest);
        transactionSenderPrespec.setDescription("Amount transferred to wallet " + receiverAccount.getAccountNumber());
        transactionSenderPrespec.setTransactionTime(LocalDateTime.now());
        transactionSenderPrespec.setTransactionId(transactionId);
        transactionRepo.save(transactionSenderPrespec);

        Transaction transactionReceiverPrespec = transactionMapper.toTransaction(transactionRequest);
        transactionReceiverPrespec.setDescription("Amount received from wallet " + senderAccount.getAccountNumber());
        transactionReceiverPrespec.setTransactionTime(LocalDateTime.now());
        transactionReceiverPrespec.setTransactionType(CREDIT);
        transactionReceiverPrespec.setTransactionId(transactionId);
        transactionRepo.save(transactionReceiverPrespec);
    }

    private void validateAndTransferAmount(Account senderAccount, Account receiverAccount, double transferAmount, double cashback) {
        double intialSenderAccBal = senderAccount.getBalance();
        double intialReceiverAccBal = receiverAccount.getBalance();
        if (transferAmount > intialSenderAccBal) {
            throw new GlobalWalletException("Insufficient balance in senders wallet", LocalDateTime.now(), GlobalErrorCode.INSUFFICIENT_BALANCE);
        }
        validateAmount(transferAmount);
        senderAccount.setBalance(intialSenderAccBal - transferAmount + cashback);
        receiverAccount.setBalance(intialReceiverAccBal + transferAmount);
        accountRepo.save(senderAccount);
        accountRepo.save(receiverAccount);
    }

    private Account validateAccountExists(String accountNumber) {
        Optional<Account> account = accountRepo.findById(accountNumber);

        if (account.isPresent()) {
            return account.get();
        } else {
            throw new EntityNotFoundException("Wallet with wallet id " + accountNumber + " does not exists");
        }
    }


    @Transactional
    public TransactionResponse addMoney(TransactionRequest transactionRequest) {
        double amountToBeAdded = transactionRequest.getAmount();
        validateAmount(amountToBeAdded);
        Account account = validateAccountExists(transactionRequest.getSenderAccountNumber());
        double initialAmount = account.getBalance();
        double cashback = calculateCashback(amountToBeAdded);
        account.setBalance(initialAmount + amountToBeAdded + cashback);
        accountRepo.save(account);

//      Saving the transaction in collection
        String transactionId = generateTransactionId();


        Transaction transaction = transactionMapper.toTransaction(transactionRequest);
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setDescription("Money added to wallet successfully");
        transaction.setReceiverAccountNumber("NA");
        transaction.setTransactionId(transactionId);
        transactionRepo.save(transaction);

//      Saving Cashback in collection
        saveCashback(account, cashback, transactionId, true);

//      Queuing the email sending task to redis
        emailService.sendEmail(EmailDetails.builder()
                .receiverEmail(account.getEmail())
                .body(transaction.getDescription())
                .subject("Money Added To Wallet")
                .build());

        return TransactionResponse.builder()
                .cashbackReceived(cashback)
                .description("Money added to wallet")
                .timestamp(LocalDateTime.now())
                .status("SUCCESS")
                .build();
    }

    private void validateAmount(double amountToBeAdded) {
        if (amountToBeAdded <= 0) {
            throw new GlobalWalletException("Amount entered can't be less than 0", LocalDateTime.now(), GlobalErrorCode.INVALID_AMOUNT);
        }
    }

    public List<Transaction> getTransactions(String accountNo) {
        return transactionRepo.findBySenderAccountNumber(accountNo);
    }

    public List<Cashback> getCashbacks(String accountNo) {
        return cashbackRepo.findByAccountNumber(accountNo);
    }

    public double calculateCashback(double amount) {
        int cashbackPercentage = random.nextInt(10) + 1;
        return ((amount * cashbackPercentage) / 100);
    }

    public String generateTransactionId() {
        return UUID.randomUUID().toString();
    }
}
