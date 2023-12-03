package com.walletapplication.payme.model.exceptions;

import java.time.LocalDateTime;

public class EntityNotFoundException extends GlobalWalletException{
    public EntityNotFoundException(){
        super("Requested entity does not exist",LocalDateTime.now(),GlobalErrorCode.ERROR_WALLET_NOT_FOUND);
    }

    public EntityNotFoundException(String message){
        super(message,LocalDateTime.now(),GlobalErrorCode.ERROR_WALLET_NOT_FOUND);
    }
}
