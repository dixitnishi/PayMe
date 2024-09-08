package com.walletapplication.payme.model.exceptions;

import java.time.LocalDateTime;

import static com.walletapplication.payme.model.exceptions.GlobalErrorCode.INVALID_EMAIL;

public class EntityAlreadyExistsException extends GlobalWalletException{

    public EntityAlreadyExistsException(){
        super("Requested Entity Already Exists", LocalDateTime.now(),INVALID_EMAIL);
    }

    public EntityAlreadyExistsException(String message){
        super(message, LocalDateTime.now(),INVALID_EMAIL);
    }


}
